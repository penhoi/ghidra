/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.app.plugin.core.functiongraph.mvc;

import java.awt.Color;

import ghidra.framework.options.ToolOptions;
import ghidra.framework.plugintool.Plugin;
import ghidra.graph.viewer.options.*;
import ghidra.program.model.symbol.FlowType;
import ghidra.util.HelpLocation;

public class FunctionGraphOptions extends VisualGraphOptions {

	private static final String EDGE_FALLTHROUGH_HIGHLIGHT_COLOR_KEY =
		"Edge Color - Fallthrough Highlight";
	private static final String EDGE_UNCONDITIONAL_JUMP_HIGHLIGHT_COLOR_KEY =
		"Edge Color - Unconditional Jump Highlight";
	private static final String EDGE_CONDITIONAL_JUMP_HIGHLIGHT_COLOR_KEY =
		"Edge Color - Conditional Jump Highlight";
	private static final String EDGE_FALLTHROUGH_COLOR_KEY = "Edge Color - Fallthrough ";
	private static final String EDGE_UNCONDITIONAL_JUMP_COLOR_KEY =
		"Edge Color - Unconditional Jump ";
	private static final String EDGE_COLOR_CONDITIONAL_JUMP_KEY = "Edge Color - Conditional Jump ";

	public static final String RELAYOUT_OPTIONS_KEY = "Automatic Graph Relayout";
	public static final String RELAYOUT_OPTIONS_DESCRIPTION = "Signals to the Function Graph " +
		"when an automatic relayout of the graph should take place.  The  basic options are:<ul>" +
		"<li><b>Always</b> - always relayout the graph when the block model changes</li>" +
		"<li><b>Block Model Changes Only</b> - relayout the graph when the block model changes " +
		"(like when a label has been added to the program in the currently graphed function)</li>" +
		"<li><b>Vertex Grouping Changes Only</b> - when vertices are grouped or ungrouped</li>" +
		"<li><b>Never</b> - do not automatically relayout the graph</li></ul>" + "<br><br>" +
		"<b><i>See help for more</i></b>";

	private static final String DEFAULT_GROUP_BACKGROUND_COLOR_KEY = "Default Group Color";
	private static final String DEFAULT_GROUP_BACKGROUND_COLOR_DESCRPTION =
		"The default " + "background color applied to newly created group vertices";

	private static final String UPDATE_GROUP_AND_UNGROUP_COLORS =
		"Update Vertex Colors When Grouping";
	private static final String UPDATE_GROUP_AND_UNGROUP_COLORS_DESCRIPTION =
		"Signals that any user color changes to a group vertex will apply that same color to " +
			"all grouped vertices as well.";

	public static final Color DEFAULT_GROUP_BACKGROUND_COLOR = new Color(226, 255, 155);
	private static final Color HOVER_HIGHLIGHT_FALL_THROUGH_COLOR = new Color(255, 127, 127);
	private static final Color HOVER_HIGHLIGHT_UNCONDITIONAL_COLOR = new Color(127, 127, 255);
	private static final Color HOVER_HIGHLIGHT_CONDITIONAL_COLOR = Color.GREEN;

	private boolean updateGroupColorsAutomatically = true;
	private Color defaultGroupBackgroundColor = DEFAULT_GROUP_BACKGROUND_COLOR;

	private Color fallthroughEdgeColor = Color.RED;
	private Color unconditionalJumpEdgeColor = Color.BLUE;
	private Color conditionalJumpEdgeColor = Color.GREEN.darker().darker();

	private Color fallthroughEdgeHighlightColor = HOVER_HIGHLIGHT_FALL_THROUGH_COLOR;
	private Color unconditionalJumpEdgeHighlightColor = HOVER_HIGHLIGHT_UNCONDITIONAL_COLOR;
	private Color conditionalJumpEdgeHighlightColor = HOVER_HIGHLIGHT_CONDITIONAL_COLOR;

	protected RelayoutOption relayoutOption = RelayoutOption.NEVER;

	public Color getDefaultGroupBackgroundColor() {
		return defaultGroupBackgroundColor;
	}

	public boolean getUpdateGroupColorsAutomatically() {
		return updateGroupColorsAutomatically;
	}

	public Color getFallthroughEdgeColor() {
		return fallthroughEdgeColor;
	}

	public Color getUnconditionalJumpEdgeColor() {
		return unconditionalJumpEdgeColor;
	}

	public Color getConditionalJumpEdgeColor() {
		return conditionalJumpEdgeColor;
	}

	public Color getUnconditionalJumpEdgeHighlightColor() {
		return unconditionalJumpEdgeHighlightColor;
	}

	public Color getFallthroughEdgeHighlightColor() {
		return fallthroughEdgeHighlightColor;
	}

	public Color getConditionalJumpEdgeHighlightColor() {
		return conditionalJumpEdgeHighlightColor;
	}

	public RelayoutOption getRelayoutOption() {
		return relayoutOption;
	}

	public void initializeOptions(Plugin plugin, ToolOptions options) {
		HelpLocation help = new HelpLocation(plugin.getName(), "Options");
		options.setOptionsHelpLocation(help);

		options.registerOption(RELAYOUT_OPTIONS_KEY, RelayoutOption.VERTEX_GROUPING_CHANGES, help,
			RELAYOUT_OPTIONS_DESCRIPTION);

		options.registerOption(SHOW_ANIMATION_OPTIONS_KEY, useAnimation(), help,
			SHOW_ANIMATION_DESCRIPTION);

		options.registerOption(USE_MOUSE_RELATIVE_ZOOM, useMouseRelativeZoom(), help,
			USE_MOUSE_RELATIVE_ZOOM_DESCRIPTION);

		options.registerOption(USE_CONDENSED_LAYOUT, useCondensedLayout(),
			new HelpLocation(plugin.getName(), "Layout_Compressing"),
			USE_CONDENSED_LAYOUT_DESCRIPTION);

		options.registerOption(VIEW_RESTORE_OPTIONS_KEY, ViewRestoreOption.START_FULLY_ZOOMED_OUT,
			help, VIEW_RESTORE_OPTIONS_DESCRIPTION);

		options.registerOption(SCROLL_WHEEL_PANS_KEY, getScrollWheelPans(), help,
			SCROLL_WHEEL_PANS_DESCRIPTION);

		options.registerOption(DEFAULT_GROUP_BACKGROUND_COLOR_KEY, DEFAULT_GROUP_BACKGROUND_COLOR,
			help, DEFAULT_GROUP_BACKGROUND_COLOR_DESCRPTION);

		options.registerOption(UPDATE_GROUP_AND_UNGROUP_COLORS, updateGroupColorsAutomatically,
			help, UPDATE_GROUP_AND_UNGROUP_COLORS_DESCRIPTION);

		options.registerOption(EDGE_COLOR_CONDITIONAL_JUMP_KEY, conditionalJumpEdgeColor, help,
			"Conditional jump edge color");

		options.registerOption(EDGE_UNCONDITIONAL_JUMP_COLOR_KEY, unconditionalJumpEdgeColor, help,
			"Unconditional jump edge color");

		options.registerOption(EDGE_FALLTHROUGH_COLOR_KEY, fallthroughEdgeColor, help,
			"Fallthrough edge color");

		options.registerOption(EDGE_CONDITIONAL_JUMP_HIGHLIGHT_COLOR_KEY,
			conditionalJumpEdgeHighlightColor, help,
			"Conditional jump edge color when highlighting the reachablity of a vertex");

		options.registerOption(EDGE_UNCONDITIONAL_JUMP_HIGHLIGHT_COLOR_KEY,
			unconditionalJumpEdgeHighlightColor, help,
			"Unconditional jump edge color when highlighting the reachablity of a vertex");

		options.registerOption(EDGE_FALLTHROUGH_HIGHLIGHT_COLOR_KEY, fallthroughEdgeHighlightColor,
			help, "Fallthrough edge color when highlighting the reachablity of a vertex");

	}

	public void loadOptions(Plugin plugin, ToolOptions options) {
		conditionalJumpEdgeColor =
			options.getColor(EDGE_COLOR_CONDITIONAL_JUMP_KEY, conditionalJumpEdgeColor);

		unconditionalJumpEdgeColor =
			options.getColor(EDGE_UNCONDITIONAL_JUMP_COLOR_KEY, unconditionalJumpEdgeColor);

		fallthroughEdgeColor = options.getColor(EDGE_FALLTHROUGH_COLOR_KEY, fallthroughEdgeColor);

		conditionalJumpEdgeHighlightColor = options.getColor(
			EDGE_CONDITIONAL_JUMP_HIGHLIGHT_COLOR_KEY, conditionalJumpEdgeHighlightColor);

		unconditionalJumpEdgeHighlightColor = options.getColor(
			EDGE_UNCONDITIONAL_JUMP_HIGHLIGHT_COLOR_KEY, unconditionalJumpEdgeHighlightColor);

		fallthroughEdgeHighlightColor =
			options.getColor(EDGE_FALLTHROUGH_HIGHLIGHT_COLOR_KEY, fallthroughEdgeHighlightColor);

		relayoutOption =
			options.getEnum(RELAYOUT_OPTIONS_KEY, RelayoutOption.VERTEX_GROUPING_CHANGES);

		useAnimation = options.getBoolean(SHOW_ANIMATION_OPTIONS_KEY, useAnimation);

		useMouseRelativeZoom = options.getBoolean(USE_MOUSE_RELATIVE_ZOOM, useMouseRelativeZoom);

		useCondensedLayout = options.getBoolean(USE_CONDENSED_LAYOUT, useCondensedLayout);

		viewRestoreOption =
			options.getEnum(VIEW_RESTORE_OPTIONS_KEY, ViewRestoreOption.START_FULLY_ZOOMED_OUT);

		scrollWheelPans = options.getBoolean(SCROLL_WHEEL_PANS_KEY, scrollWheelPans);

		defaultGroupBackgroundColor =
			options.getColor(DEFAULT_GROUP_BACKGROUND_COLOR_KEY, DEFAULT_GROUP_BACKGROUND_COLOR);

		updateGroupColorsAutomatically =
			options.getBoolean(UPDATE_GROUP_AND_UNGROUP_COLORS, updateGroupColorsAutomatically);
	}

	public Color getColor(FlowType flowType) {
		if (flowType.isFallthrough()) {
			return getFallthroughEdgeColor();
		}
		else if (flowType.isUnConditional()) {
			return getUnconditionalJumpEdgeColor();
		}
		else if (flowType.isJump()) {
			return getConditionalJumpEdgeColor();
		}

		return Color.BLACK;
	}

	public Color getHighlightColor(FlowType flowType) {
		if (flowType.isFallthrough()) {
			return getFallthroughEdgeHighlightColor();
		}
		else if (flowType.isUnConditional()) {
			return getUnconditionalJumpEdgeHighlightColor();
		}
		else if (flowType.isJump()) {
			return getConditionalJumpEdgeHighlightColor();
		}

		return Color.BLACK;
	}
}