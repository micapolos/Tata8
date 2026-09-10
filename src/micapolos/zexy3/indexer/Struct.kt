package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Struct
import micapolos.zexy3.model.Struct as ModelStruct

fun Indexer.indexed(model: ModelStruct): Struct =
  when (model) {
    is ModelStruct.Make -> Struct.Make(model.name, model.values.map { indexed(it) })
  }