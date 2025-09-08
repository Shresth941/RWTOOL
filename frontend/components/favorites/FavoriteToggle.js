// FavoriteToggle.js
import React, { useState } from "react";
import favoritesService from "../../services/favoritesService";

const FavoriteToggle = ({ userId, reportId, initial = false }) => {
  const [fav, setFav] = useState(initial);

  const toggle = async () => {
    try {
      if (fav) {
        await favoritesService.removeByUserReport(userId, reportId);
      } else {
        await favoritesService.add({ userId, reportId });
      }
      setFav(!fav);
    } catch (err) {
      console.error(err);
      alert("Failed to update favorite");
    }
  };

  return <button className={`btn btn-sm ${fav ? "btn-warning" : "btn-outline-secondary"}`} onClick={toggle}>{fav ? "Unfav" : "Fav"}</button>;
};

export default FavoriteToggle;
