package io.instah.auron.sdk.permissions

import io.instah.auron.sdk.Signal

object PermissionManagerCommon {
    var onPermissionDecision = Signal<PermissionDecisionResult>()
}