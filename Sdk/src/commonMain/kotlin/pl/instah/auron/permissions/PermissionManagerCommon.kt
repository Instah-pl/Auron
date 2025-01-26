package pl.instah.auron.permissions

import pl.instah.auron.Signal

object PermissionManagerCommon {
    var onPermissionDecision = Signal<PermissionDecisionResult>()
}