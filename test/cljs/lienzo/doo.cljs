(ns lienzo.doo
  (:require [doo.runner :refer-macros [doo-tests doo-all-tests]]
                                      [lienzo.tests]))

(doo-tests 'lienzo.button-test)
