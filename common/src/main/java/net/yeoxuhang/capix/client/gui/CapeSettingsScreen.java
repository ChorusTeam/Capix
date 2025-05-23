package net.yeoxuhang.capix.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.yeoxuhang.capix.config.CapixConfig;
import net.yeoxuhang.capix.config.CapixManager;
import net.yeoxuhang.capix.api.ModCape;
import net.yeoxuhang.capix.api.CapixApi;
import net.yeoxuhang.capix.client.Cape;

import java.util.*;

public class CapeSettingsScreen extends Screen {
    private static final Component TITLE = Component.literal("Cape Settings");
    private static final int HEADER_HEIGHT = 33;
    private static final int FOOTER_HEIGHT = 58;
    private float tick = 0;

    private final Screen parent;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, HEADER_HEIGHT, FOOTER_HEIGHT);


    private CapeSettingsList capeList;

    public CapeSettingsScreen(Screen parent) {
        super(TITLE);
        this.parent = parent;
        CapixApi.reload();
    }

    @Override
    protected void init() {
        this.capeList = new CapeSettingsList(this.minecraft);
        this.layout.addToContents(this.capeList);

        this.layout.addTitleHeader(TITLE, this.font);
        LinearLayout footer = this.layout.addToFooter(LinearLayout.vertical()).spacing(5);
        footer.defaultCellSetting().alignHorizontallyCenter();
        footer.addChild(Button.builder(CommonComponents.GUI_DONE, (btn) -> onClose()).width(200).build());
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    public void repositionElements() {
        this.layout.arrangeElements();
        if (this.capeList != null) {
            this.capeList.updateSize(this.width, this.layout);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        MultiBufferSource.BufferSource vertexConsumers = Minecraft.getInstance().renderBuffers().bufferSource();
        Cape cape = new Cape(Cape.createBodyLayer().bakeRoot());

        UUID name = Minecraft.getInstance().getGameProfile().getId();
        ResourceLocation capeTexture = CapixManager.getCapeForPlayer(name.toString());
        int xPosition = this.width / 2 + 160;
        int y = this.height / 2 - 20;

        if (capeTexture != null) {
            VertexConsumer base = vertexConsumers.getBuffer(RenderType.entityTranslucentEmissive(capeTexture));
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            poseStack.pushPose();
            poseStack.translate(xPosition, y - 80, 110);
            poseStack.scale(100F, 100F, 100F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-30));
            poseStack.mulPose(Axis.YP.rotationDegrees(-45));
            tick++;
            cape.renderWithAnimation(poseStack, base, 15728880, OverlayTexture.NO_OVERLAY, -1, tick);
            vertexConsumers.endBatch();
            poseStack.popPose();
            RenderSystem.disableBlend();
        }
    }

    @Override
    public void onClose() {
        CapixConfig.getInstance().save();
        this.minecraft.setScreen(parent);
    }

    class CapeSettingsList extends ObjectSelectionList<CapeSettingsList.Entry> {

        public CapeSettingsList(Minecraft mc) {
            super(mc, CapeSettingsScreen.this.width, CapeSettingsScreen.this.height - HEADER_HEIGHT - FOOTER_HEIGHT, HEADER_HEIGHT, 18);
            loadEntries();
        }

        private void loadEntries() {
            for (String modId : CapixApi.getAllModIds()) {
                String displayName = Arrays.stream(modId.split("_"))
                        .map(s -> s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1))
                        .reduce((a, b) -> a + " " + b)
                        .orElse(modId);
                this.addEntry(new Entry(Component.literal(displayName), true, null, null, null));

                Map<String, ModCape> capes = CapixApi.getCapesForMod(modId);
                Map<String, Boolean> states = CapixConfig.getInstance().getCapeStatesForMod(modId);

                for (Map.Entry<String, ModCape> entry : capes.entrySet()) {
                    String capeName =  entry.getKey();
                    boolean enabled = states.getOrDefault(capeName, false);
                    Component label = Component.literal("  " + capeName);
                    this.addEntry(new Entry(label, false, modId, capeName, enabled));
                }
            }
        }

        class Entry extends ObjectSelectionList.Entry<Entry> {
            private final Component label;
            private final boolean isHeader;
            private final String modId;
            private final String capeName;
            private final Checkbox checkbox;

            Entry(Component label, boolean isHeader, String modId, String capeName, Boolean enabled) {
                this.label = label;
                this.isHeader = isHeader;
                this.modId = modId;
                this.capeName = capeName;

                if (!isHeader && modId != null && capeName != null && enabled != null) {
                    ModCape cape = CapixApi.getCapesForMod(modId).get(capeName);
                    String playerName = Minecraft.getInstance().getUser().getProfileId().toString();

                    if (cape != null && cape.shouldRenderFor(playerName)) {
                        this.checkbox = Checkbox.builder(label, CapeSettingsScreen.this.font)
                                .maxWidth(240)
                                .selected(enabled)
                                .onValueChange((box, value) -> {
                                    CapixConfig.getInstance().clearAllCapeStates();
                                    if (value) {
                                        CapixConfig.getInstance().setCapeEnabled(modId, capeName, true);
                                    }
                                    CapixConfig.getInstance().save();

                                    for (Entry entry : CapeSettingsList.this.children()) {
                                        if (!entry.isHeader && entry.checkbox != null) {
                                            boolean shouldBeChecked = value && entry.modId.equals(modId) && entry.capeName.equals(capeName);
                                            entry.checkbox.selected = shouldBeChecked;
                                        }
                                    }
                                }).build();
                    } else {
                        this.checkbox = null; // Hide checkbox if player is not in the name list
                    }
                } else {
                    this.checkbox = null;
                }
            }

            @Override
            public void render(GuiGraphics gui, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTicks) {
                if (this.isHeader) {
                    gui.drawCenteredString(CapeSettingsScreen.this.font, this.label, x + entryWidth / 2, y + 4, 0xFFFF00);
                } else if (checkbox != null) {
                    checkbox.setX(x + 5);
                    checkbox.setY(y);
                    checkbox.render(gui, mouseX, mouseY, partialTicks);
                }
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return checkbox != null && checkbox.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return checkbox != null && checkbox.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public Component getNarration() {
                return label;
            }
        }

        @Override
        public int getRowWidth() {
            return 140;
        }
    }
}