package com.edittextpicker.aliazaz

data class Values(var minvalue: Float = -1f,
                  var maxvalue: Float = -1f,
                  var rangedefaultvalue: Float = -1f,
                  var defaultvalue: String? = null,
                  var mask: String? = null,
                  var pattern: String? = null,
                  var type: Int? = null,
                  var required: Boolean? = null)