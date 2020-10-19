(ns lienzo.select-test
  (:require [cljs.test :refer-macros [is testing async]]
            [devcards.core :refer-macros [deftest defcard defcard-rg]]
            [sablono.core :as sab]
            [reagent.core :as r]
            [lienzo.components.select :refer [select]]))

(defcard
  (str "# Select \n"
       "This namespace defines some functions about select component"))

(defcard-rg test-select-box
  [select])

(defcard-rg test-select-box-arity-1-string
  [select "Select:"])

(defcard-rg test-select-box-arity-1-map
  [select {:placeholder "Enter your choose"}])

(defcard-rg test-select-box-arity-1-coll
  [select ["foo" "bar" "baz"]])

(defcard-rg test-selected-box-arity-1-large
  [select [[:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Afghanistan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇽"] "Aland Islands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇱"] "Albania"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇩🇿"] "Algeria"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇸"] "American Samoa"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇩"] "Andorra"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇴"] "Angola"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇮"] "Anguilla"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇶"] "Antarctica"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇬"] "Antigua and Barbuda"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇷"] "Argentina"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇲"] "Armenia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇼"] "Aruba"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇺"] "Australia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇹"] "Austria"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇿"] "Azerbaijan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇸"] "Bahamas"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇭"] "Bahrain"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇩"] "Bangladesh"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇧"] "Barbados"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇾"] "Belarus"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇪"] "Belgium"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇿"] "Belize"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇯"] "Benin"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇲"] "Bermuda"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇹"] "Bhutan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇴"] "Bolivia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇦"] "Bosnia Herzegovina"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇼"] "Botswana"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇻"] "Bouvet Island"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇷"] "Brazil"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇴"] "British Indian Ocean Territory"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇳"] "Brunei"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇬"] "Bulgaria"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇫"] "Burkina Faso"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇮"] "Burundi"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇰🇭"] "Cambodia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇲"] "Cameroon"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇦"] "Canada"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇻"] "Cape Verde"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇧🇶"] "Caribbean Netherlands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇰🇾"] "Cayman Islands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇫"] "Central African Republic"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇹🇩"] "Chad"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇱"] "Chile"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇳"] "China"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇽"] "Christmas Island"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇨"] "Cocos (Keeling) Islands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇴"] "Colombia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇰🇲"] "Comoros"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇬"] "Republic of the Congo"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇩"] "Congo (Democratic Rep)"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇰"] "Cook Islands"]           
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇷"] "Costa Rica"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇭🇷"] "Croatia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇺"] "Cuba"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇼"] "Curaçao"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇾"] "Cyprus"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇿"] "Czech Republic"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇩🇰"] "Denmark"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇩🇯"] "Djibouti"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇩🇲"] "Dominica"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇩🇴"] "Dominican Republic"]
           [:span [:span {:style {:width "20px" :float "right"}} ""] "East Timor"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇪🇨"] "Ecuador"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇪🇬"] "Egypt"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇸🇻"] "El Salvador"]
           [:span [:span {:style {:width "20px" :float "right"}} "🏴󠁧󠁢󠁥󠁮󠁧󠁿"] "England"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇶"] "Equatorial Guinea"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇪🇷"] "Eritrea"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇪🇪"] "Estonia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇸🇿"] "Eswatini (Swaziland)"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇪🇹"] "Ethiopia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇫🇰"] "Falkland Islands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇫🇴"] "Faroe Islands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇫🇯"] "Fiji"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇫🇮"] "Finland"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇫🇷"] "France"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇫"] "French Guiana"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇵🇫"] "French Polynesia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇹🇫"] "French Southern and Antarctic Lands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇦"] "Gabon"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇲"] "Gambia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇪"] "Georgia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇩🇪"] "Germany"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇭"] "Ghana"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇮"] "Gibraltar"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇷"] "Greece"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇱"] "Greenland"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇩"] "Grenada"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇵"] "Guadeloupe"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇺"] "Guam"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇹"] "Guatemala"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇬"] "Guernsey"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇳"] "Guinea"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇼"] "Guinea-Bissau"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇬🇾"] "Guyana"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇭🇹"] "Haiti"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇭🇲"] "Heard Island and McDonald Islands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇭🇳"] "Honduras"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇭🇺"] "Hungary"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇸"] "Iceland"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇳"] "India"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇩"] "Indonesia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇷"] "Iran"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇶"] "Iraq"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇪"] "Ireland"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇲"] "Isle of Man"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇱"] "Israel"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇮🇹"] "Italy"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇨🇮"] "Ivory Coast"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Jamaica"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Japan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Jordan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Kazakhstan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Kenya"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Kiribati"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Korea North"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Korea South"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Kosovo"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Kuwait"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Kyrgyzstan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Laos"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Latvia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Lebanon"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Lesotho"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Liberia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Libya"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Liechtenstein"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Lithuania"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Luxembourg"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Macedonia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Madagascar"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Malawi"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Malaysia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Maldives"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Mali"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Malta"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Marshall Islands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Mauritania"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Mauritius"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Mexico"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Micronesia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Moldova"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Monaco"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Mongolia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Montenegro"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Morocco"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Mozambique"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Myanmar, {Burma}"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Namibia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Nauru"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Nepal"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Netherlands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "New Zealand"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Nicaragua"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Niger"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Nigeria"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Norway"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Oman"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Pakistan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Palau"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Panama"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Papua New Guinea"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Paraguay"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Peru"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Philippines"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Poland"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Portugal"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Qatar"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Romania"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Russian Federation"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Rwanda"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "St Kitts & Nevis"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "St Lucia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Saint Vincent & the Grenadines"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Samoa"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "San Marino"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Sao Tome & Principe"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Saudi Arabia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Senegal"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Serbia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Seychelles"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Sierra Leone"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Singapore"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Slovakia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Slovenia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Solomon Islands"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Somalia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "South Africa"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "South Sudan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Spain"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Sri Lanka"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Sudan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Suriname"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Swaziland"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Sweden"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Switzerland"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Syria"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Taiwan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Tajikistan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Tanzania"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Thailand"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Togo"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Tonga"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Trinidad & Tobago"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Tunisia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Turkey"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Turkmenistan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Tuvalu"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Uganda"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Ukraine"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "United Arab Emirates"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "United Kingdom"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "United States"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Uruguay"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Uzbekistan"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Vanuatu"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Vatican City"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Venezuela"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Vietnam"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Yemen"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Zambia"]
           [:span [:span {:style {:width "20px" :float "right"}} "🇦🇫"] "Zimbabwe"]]])

(defcard-rg test-select-box-arity-1-hiccup
  [select [[:div {:style {:color "green"}} "foo"] [:div {:style {:color "purple"}} "bar"]]])
