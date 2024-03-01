export type JobCategory =
  | "AGRICULTURE_VITICULTURE_PECHERIE"
  | "HOTELLERIE_PLEIN_AIR_CLUB_VACANCES_CAMPING_ANIMATION"
  | "HOTELS_CAFES_BARS_RESTAURANTS"
  | "EVENEMENTIEL"
  | "CASINOS_PARCS_ATTRACTION"
  | "ADMINISTRATION_ESPACES_CULTURELS_TOURISME"
  | "MONTAGNE_SKI"
  | "MER_PLONGEE_SPORTS_LOISIRS_NAUTIQUES"
  | "SECURITE_GARDIENNAGE"
  | "LOGISTIQUE_TRANSPORT"
  | "BABY_SITTING_SERVICES_PERSONNE"
  | "COMMERCE_ACHATS_VENTE"
  | "SPA_ESTHETIQUE_COIFFURE";

export function jobCategoryToString(category: JobCategory): string {
  switch (category) {
    case "AGRICULTURE_VITICULTURE_PECHERIE":
      return "Agriculture";
    case "HOTELLERIE_PLEIN_AIR_CLUB_VACANCES_CAMPING_ANIMATION":
      return "Holiday clubs, campings";
    case "HOTELS_CAFES_BARS_RESTAURANTS":
      return "Hotels, coffee shops, bars, restaurants";
    case "EVENEMENTIEL":
      return "Events";
    case "CASINOS_PARCS_ATTRACTION":
      return "Casinos, amusement parks";
    case "ADMINISTRATION_ESPACES_CULTURELS_TOURISME":
      return "Administration, tourism";
    case "MONTAGNE_SKI":
      return "Mountains, skiing";
    case "MER_PLONGEE_SPORTS_LOISIRS_NAUTIQUES":
      return "Sea, diving, water sports";
    case "SECURITE_GARDIENNAGE":
      return "Security";
    case "LOGISTIQUE_TRANSPORT":
      return "Logistics, transport";
    case "BABY_SITTING_SERVICES_PERSONNE":
      return "Baby-sitting, personal services";
    case "COMMERCE_ACHATS_VENTE":
      return "Commerce, sales";
    case "SPA_ESTHETIQUE_COIFFURE":
      return "Beauty salon, spa, hairdressing";
  }
}
