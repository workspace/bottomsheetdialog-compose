package com.holix.android.bottomsheetdialog.compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.view.*
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.Px
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.*
import androidx.compose.ui.semantics.dialog
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*


/**
 * Properties used to customize [BottomSheetDialog].
 *
 * @property dismissOnBackPress whether the dialog can be dismissed by pressing the back button.
 * If true, pressing the back button will call onDismissRequest.
 * @property dismissOnClickOutside whether the dialog can be dismissed by clicking outside the
 * dialog's bounds. If true, clicking outside the dialog will call onDismissRequest.
 * @property dismissWithAnimation [BottomSheetDialog.setDismissWithAnimation]
 * @property securePolicy Policy for setting [WindowManager.LayoutParams.FLAG_SECURE] on the
 * dialog's window.
 * @property navigationBarColor  Color to apply to the navigationBar.
 */
@Immutable
class BottomSheetDialogProperties constructor(
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = true,
    val dismissWithAnimation: Boolean = false,
    val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
    val navigationBarProperties: NavigationBarProperties = NavigationBarProperties(),
    val behaviorProperties: BottomSheetBehaviorProperties = BottomSheetBehaviorProperties()
) {

    @Deprecated("Use NavigationBarProperties(color = navigationBarColor) instead")
    constructor(
        dismissOnBackPress: Boolean = true,
        dismissOnClickOutside: Boolean = true,
        dismissWithAnimation: Boolean = false,
        securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
        navigationBarColor: Color
    ) : this(
        dismissOnBackPress,
        dismissOnClickOutside,
        dismissWithAnimation,
        securePolicy,
        NavigationBarProperties(color = navigationBarColor)
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BottomSheetDialogProperties) return false

        if (dismissOnBackPress != other.dismissOnBackPress) return false
        if (dismissOnClickOutside != other.dismissOnClickOutside) return false
        if (dismissWithAnimation != other.dismissWithAnimation) return false
        if (securePolicy != other.securePolicy) return false
        if (navigationBarProperties != other.navigationBarProperties) return false
        if (behaviorProperties != other.behaviorProperties) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dismissOnBackPress.hashCode()
        result = 31 * result + dismissOnClickOutside.hashCode()
        result = 31 * result + dismissWithAnimation.hashCode()
        result = 31 * result + securePolicy.hashCode()
        result = 31 * result + navigationBarProperties.hashCode()
        result = 31 * result + behaviorProperties.hashCode()
        return result
    }
}

/**
 * Properties used to set [com.google.android.material.bottomsheet.BottomSheetBehavior].
 *
 * @see [com.google.android.material.bottomsheet.BottomSheetBehavior]
 */
@Immutable
class BottomSheetBehaviorProperties(
    val state: State = State.Collapsed,
    val maxWidth: Size = Size.NotSet,
    val maxHeight: Size = Size.NotSet,
    val isDraggable: Boolean = true,
    @IntRange(from = 0)
    val expandedOffset: Int = 0,
    @FloatRange(from = 0.0, to = 1.0, fromInclusive = false, toInclusive = false)
    val halfExpandedRatio: Float = 0.5F,
    val isHideable: Boolean = true,
    val peekHeight: PeekHeight = PeekHeight.Auto,
    val isFitToContents: Boolean = true,
    val skipCollapsed: Boolean = false,
    val isGestureInsetBottomIgnored: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BottomSheetBehaviorProperties) return false

        if (state != other.state) return false
        if (maxWidth != other.maxWidth) return false
        if (maxHeight != other.maxHeight) return false
        if (isDraggable != other.isDraggable) return false
        if (expandedOffset != other.expandedOffset) return false
        if (halfExpandedRatio != other.halfExpandedRatio) return false
        if (isHideable != other.isHideable) return false
        if (peekHeight != other.peekHeight) return false
        if (isFitToContents != other.isFitToContents) return false
        if (skipCollapsed != other.skipCollapsed) return false
        if (isGestureInsetBottomIgnored != other.isGestureInsetBottomIgnored) return false

        return true
    }

    override fun hashCode(): Int {
        var result = state.hashCode()
        result = 31 * result + maxWidth.hashCode()
        result = 31 * result + maxHeight.hashCode()
        result = 31 * result + isDraggable.hashCode()
        result = 31 * result + expandedOffset.hashCode()
        result = 31 * result + halfExpandedRatio.hashCode()
        result = 31 * result + isHideable.hashCode()
        result = 31 * result + peekHeight.hashCode()
        result = 31 * result + isFitToContents.hashCode()
        result = 31 * result + skipCollapsed.hashCode()
        result = 31 * result + isGestureInsetBottomIgnored.hashCode()
        return result
    }

    @Immutable
    enum class State {
        @Stable
        Expanded,
        @Stable
        HalfExpanded,
        @Stable
        Collapsed
    }

    @JvmInline
    @Immutable
    value class Size(@Px val value: Int) {

        companion object {
            @Stable
            val NotSet = Size(-1)
        }
    }

    @JvmInline
    @Stable
    value class PeekHeight(val value: Int) {

        companion object {
            @Stable
            val Auto = PeekHeight(PEEK_HEIGHT_AUTO)
        }
    }
}

/**
 * Properties used to customize navigationBar.

 * @param color The **desired** [Color] to set. This may require modification if running on an
 * API level that only supports white navigation bar icons. Additionally this will be ignored
 * and [Color.Transparent] will be used on API 29+ where gesture navigation is preferred or the
 * system UI automatically applies background protection in other navigation modes.
 * @param darkIcons Whether dark navigation bar icons would be preferable.
 * @param navigationBarContrastEnforced Whether the system should ensure that the navigation
 * bar has enough contrast when a fully transparent background is requested. Only supported on
 * API 29+.
 * @param transformColorForLightContent A lambda which will be invoked to transform [color] if
 * dark icons were requested but are not available. Defaults to applying a black scrim.
 *
 * Inspired by [Accompanist SystemUiController](https://github.com/google/accompanist/blob/main/systemuicontroller/src/main/java/com/google/accompanist/systemuicontroller/SystemUiController.kt)
 */

@Immutable
class NavigationBarProperties(
    val color: Color = Color.Unspecified,
    val darkIcons: Boolean = color.luminance() > 0.5f,
    val navigationBarContrastEnforced: Boolean = true,
    val transformColorForLightContent: (Color) -> Color = BlackScrimmed
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NavigationBarProperties) return false

        if (color != other.color) return false
        if (darkIcons != other.darkIcons) return false
        if (navigationBarContrastEnforced != other.navigationBarContrastEnforced) return false
        if (transformColorForLightContent != other.transformColorForLightContent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = color.hashCode()
        result = 31 * result + darkIcons.hashCode()
        result = 31 * result + navigationBarContrastEnforced.hashCode()
        return result
    }
}

private val BlackScrim = Color(0f, 0f, 0f, 0.3f) // 30% opaque black
private val BlackScrimmed: (Color) -> Color = { original ->
    BlackScrim.compositeOver(original)
}

/**
 * Opens a bottomsheet dialog with the given content.
 *
 * The dialog is visible as long as it is part of the composition hierarchy.
 * In order to let the user dismiss the BottomSheetDialog, the implementation of [onDismissRequest] should
 * contain a way to remove to remove the dialog from the composition hierarchy.
 *
 * Example usage:
 *
 * @sample com.holix.android.bottomsheetdialogcomposedemo.MainActivity
 *
 * @param onDismissRequest Executes when the user tries to dismiss the dialog.
 * @param properties [BottomSheetDialogProperties] for further customization of this dialog's behavior.
 * @param content The content to be displayed inside the dialog.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomSheetDialog(
    onDismissRequest: () -> Unit,
    properties: BottomSheetDialogProperties = BottomSheetDialogProperties(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val composition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    val dialogId = rememberSaveable { UUID.randomUUID() }
    val dialog = remember(view, density) {
        BottomSheetDialogWrapper(
            onDismissRequest,
            properties,
            view,
            layoutDirection,
            density,
            dialogId
        ).apply {
            setContent(composition) {
                BottomSheetDialogLayout(
                    Modifier
                        .nestedScroll(rememberNestedScrollInteropConnection())
                        .semantics { dialog() },
                ) {
                    currentContent()
                }
            }
        }
    }

    DisposableEffect(dialog) {
        dialog.show()

        onDispose {
            dialog.dismiss()
            dialog.disposeComposition()
        }
    }

    SideEffect {
        dialog.updateParameters(
            onDismissRequest = onDismissRequest,
            properties = properties,
            layoutDirection = layoutDirection
        )
    }
}

/**
 * Provides the underlying window of a bottomsheet dialog.
 *
 * Implemented by dialog's root layout.
 */
interface DialogWindowProvider {
    val window: Window
}

@Suppress("ViewConstructor")
private class BottomSheetDialogLayout(
    context: Context,
    override val window: Window
) : AbstractComposeView(context), DialogWindowProvider {
    private var content: @Composable () -> Unit by mutableStateOf({})

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    fun setContent(parent: CompositionContext, content: @Composable () -> Unit) {
        setParentCompositionContext(parent)
        this.content = content
        shouldCreateCompositionOnAttachedToWindow = true
        createComposition()
    }

    @Composable
    override fun Content() {
        content()
    }
}

private class BottomSheetDialogWrapper(
    private var onDismissRequest: () -> Unit,
    private var properties: BottomSheetDialogProperties,
    private val composeView: View,
    layoutDirection: LayoutDirection,
    density: Density,
    dialogId: UUID
) : BottomSheetDialog(
    /**
     * [Window.setClipToOutline] is only available from 22+, but the style attribute exists on 21.
     * So use a wrapped context that sets this attribute for compatibility back to 21.
     */
    ContextThemeWrapper(composeView.context, R.style.TransparentBottomSheetTheme)
),
    ViewRootForInspector {
    private val bottomSheetDialogLayout: BottomSheetDialogLayout

    private val bottomSheetCallbackForAnimation: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == STATE_HIDDEN) {
                onDismissRequest()
            }
        }
    }

    private val maxSupportedElevation = 30.dp

    override val subCompositionView: AbstractComposeView get() = bottomSheetDialogLayout

    // to control insets
    private val windowInsetsController = window?.let {
        WindowCompat.getInsetsController(it, it.decorView)
    }
    private var navigationBarDarkContentEnabled: Boolean
        get() = windowInsetsController?.isAppearanceLightNavigationBars == true
        set(value) {
            windowInsetsController?.isAppearanceLightNavigationBars = value
        }

    private var isNavigationBarContrastEnforced: Boolean
        get() = Build.VERSION.SDK_INT >= 29 && window?.isNavigationBarContrastEnforced == true
        set(value) {
            if (Build.VERSION.SDK_INT >= 29) {
                window?.isNavigationBarContrastEnforced = value
            }
        }

    init {
        val window = window ?: error("Dialog has no window")
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        bottomSheetDialogLayout = BottomSheetDialogLayout(context, window).apply {
            // Set unique id for AbstractComposeView. This allows state restoration for the state
            // defined inside the Dialog via rememberSaveable()
            setTag(androidx.compose.ui.R.id.compose_view_saveable_id_tag, "Dialog:$dialogId")
            // Enable children to draw their shadow by not clipping them
            clipChildren = false
            // Allocate space for elevation
            with(density) { elevation = maxSupportedElevation.toPx() }
            // Simple outline to force window manager to allocate space for shadow.
            // Note that the outline affects clickable area for the dismiss listener. In case of
            // shapes like circle the area for dismiss might be to small (rectangular outline
            // consuming clicks outside of the circle).
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, result: Outline) {
                    result.setRect(0, 0, view.width, view.height)
                    // We set alpha to 0 to hide the view's shadow and let the composable to draw
                    // its own shadow. This still enables us to get the extra space needed in the
                    // surface.
                    result.alpha = 0f
                }
            }
        }

        /**
         * Disables clipping for [this] and all its descendant [ViewGroup]s until we reach a
         * [BottomSheetDialogLayout] (the [ViewGroup] containing the Compose hierarchy).
         */
        fun ViewGroup.disableClipping() {
            clipChildren = false
            if (this is BottomSheetDialogLayout) return
            for (i in 0 until childCount) {
                (getChildAt(i) as? ViewGroup)?.disableClipping()
            }
        }

        // Turn of all clipping so shadows can be drawn outside the window
        (window.decorView as? ViewGroup)?.disableClipping()
        setContentView(bottomSheetDialogLayout)
        ViewTreeLifecycleOwner.set(bottomSheetDialogLayout, ViewTreeLifecycleOwner.get(composeView))
        ViewTreeViewModelStoreOwner.set(bottomSheetDialogLayout, ViewTreeViewModelStoreOwner.get(composeView))
        bottomSheetDialogLayout.setViewTreeSavedStateRegistryOwner(
            composeView.findViewTreeSavedStateRegistryOwner()
        )

        // Initial setup
        updateParameters(onDismissRequest, properties, layoutDirection)
    }

    private fun setLayoutDirection(layoutDirection: LayoutDirection) {
        bottomSheetDialogLayout.layoutDirection = when (layoutDirection) {
            LayoutDirection.Ltr -> android.util.LayoutDirection.LTR
            LayoutDirection.Rtl -> android.util.LayoutDirection.RTL
        }
    }

    // TODO(b/159900354): Make the Android Dialog full screen and the scrim fully transparent

    fun setContent(parentComposition: CompositionContext, children: @Composable () -> Unit) {
        bottomSheetDialogLayout.setContent(parentComposition, children)
    }

    private fun setSecurePolicy(securePolicy: SecureFlagPolicy) {
        val secureFlagEnabled =
            securePolicy.shouldApplySecureFlag(composeView.isFlagSecureEnabled())
        window!!.setFlags(
            if (secureFlagEnabled) {
                WindowManager.LayoutParams.FLAG_SECURE
            } else {
                WindowManager.LayoutParams.FLAG_SECURE.inv()
            },
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    private fun setNavigationBarProperties(properties: NavigationBarProperties) {
        with(properties) {
            navigationBarDarkContentEnabled = darkIcons
            isNavigationBarContrastEnforced = navigationBarContrastEnforced

            window?.navigationBarColor = when {
                darkIcons && windowInsetsController?.isAppearanceLightNavigationBars != true -> {
                    // If we're set to use dark icons, but our windowInsetsController call didn't
                    // succeed (usually due to API level), we instead transform the color to maintain
                    // contrast
                    transformColorForLightContent(color)
                }
                else -> color
            }.toArgb()
        }
    }

    override fun setDismissWithAnimation(dismissWithAnimation: Boolean) {
        super.setDismissWithAnimation(dismissWithAnimation)
        if (dismissWithAnimation) {
            behavior.addBottomSheetCallback(bottomSheetCallbackForAnimation)
        } else {
            behavior.removeBottomSheetCallback(bottomSheetCallbackForAnimation)
        }
    }

    private fun setBehaviorProperties(behaviorProperties: BottomSheetBehaviorProperties) {
        this.behavior.state = when (behaviorProperties.state) {
            BottomSheetBehaviorProperties.State.Expanded -> STATE_EXPANDED
            BottomSheetBehaviorProperties.State.Collapsed -> STATE_COLLAPSED
            BottomSheetBehaviorProperties.State.HalfExpanded -> STATE_HALF_EXPANDED
        }
        this.behavior.maxWidth = behaviorProperties.maxWidth.value
        this.behavior.maxHeight = behaviorProperties.maxHeight.value
        this.behavior.isDraggable = behaviorProperties.isDraggable
        this.behavior.expandedOffset = behaviorProperties.expandedOffset
        this.behavior.halfExpandedRatio = behaviorProperties.halfExpandedRatio
        this.behavior.isHideable = behaviorProperties.isHideable
        this.behavior.peekHeight = behaviorProperties.peekHeight.value
        this.behavior.isFitToContents = behaviorProperties.isFitToContents
        this.behavior.skipCollapsed = behaviorProperties.skipCollapsed
        this.behavior.isGestureInsetBottomIgnored = behaviorProperties.isGestureInsetBottomIgnored
    }

    fun updateParameters(
        onDismissRequest: () -> Unit,
        properties: BottomSheetDialogProperties,
        layoutDirection: LayoutDirection
    ) {
        this.onDismissRequest = onDismissRequest
        this.properties = properties
        setSecurePolicy(properties.securePolicy)
        setLayoutDirection(layoutDirection)
        setCanceledOnTouchOutside(properties.dismissOnClickOutside)
        setNavigationBarProperties(properties.navigationBarProperties)
        setBehaviorProperties(properties.behaviorProperties)
        dismissWithAnimation = properties.dismissWithAnimation
    }

    fun disposeComposition() {
        bottomSheetDialogLayout.disposeComposition()
    }

    override fun cancel() {
        if (properties.dismissWithAnimation) {
            // call setState(STATE_HIDDEN) -> onDismissRequest will be called in BottomSheetCallback
            super.cancel()
        } else {
            // dismiss with window animation
            onDismissRequest()
        }
    }

    @Deprecated("Deprecated")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (properties.dismissOnBackPress) {
            cancel()
        }
    }
}

@Composable
private fun BottomSheetDialogLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val width = placeables.maxByOrNull { it.width }?.width ?: constraints.minWidth
        val height = placeables.maxByOrNull { it.height }?.height ?: constraints.minHeight
        layout(width, height) {
            placeables.forEach { it.placeRelative(0, 0) }
        }
    }
}

fun View.isFlagSecureEnabled(): Boolean {
    val windowParams = rootView.layoutParams as? WindowManager.LayoutParams
    if (windowParams != null) {
        return (windowParams.flags and WindowManager.LayoutParams.FLAG_SECURE) != 0
    }
    return false
}

fun SecureFlagPolicy.shouldApplySecureFlag(isSecureFlagSetOnParent: Boolean): Boolean {
    return when (this) {
        SecureFlagPolicy.SecureOff -> false
        SecureFlagPolicy.SecureOn -> true
        SecureFlagPolicy.Inherit -> isSecureFlagSetOnParent
    }
}
