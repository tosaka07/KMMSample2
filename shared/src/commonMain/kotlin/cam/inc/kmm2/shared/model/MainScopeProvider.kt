package cam.inc.kmm2.shared.model

import com.futuremind.koru.ExportedScopeProvider
import com.futuremind.koru.ScopeProvider
import kotlinx.coroutines.MainScope

@ExportedScopeProvider
class MainScopeProvider : ScopeProvider {
    override val scope = MainScope()
}