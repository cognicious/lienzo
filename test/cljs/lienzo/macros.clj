(ns lienzo.macros)

(defmacro adhoc-render [react-render dom-render]
  `(try ~react-render
        (catch js/Error e#
            ~dom-render)))
