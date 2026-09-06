package micapolos.zexy2

import micapolos.tata8.Composite

val normalComposite = Composite.NORMAL.live
val Composite.live get() = live(Composite::class)