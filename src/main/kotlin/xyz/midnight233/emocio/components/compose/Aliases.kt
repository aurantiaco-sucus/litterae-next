package xyz.midnight233.emocio.components.compose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

typealias ComposableLambda = @Composable () -> Unit
typealias ColumnLambda = @Composable ColumnScope.() -> Unit
typealias RowLambda = @Composable RowScope.() -> Unit
typealias BoxLambda = @Composable BoxScope.() -> Unit