// FavoritesPanel.js
import React, { useEffect, useState } from "react";
import favoritesService from "../../services/favoritesService";

const FavoritesPanel = ({ userId }) => {
  const [favorites, setFavorites] = useState([]);

  useEffect(()=> {
    if (!userId) return;
    favoritesService.getByUser(userId).then(res => setFavorites(res.data || res)).catch(()=>{});
  }, [userId]);

  return (
    <div>
      <h5>Favorites</h5>
      <ul className="list-group">
        {favorites.map(f => <li key={f.id} className="list-group-item">{f.reportName || f.report?.name}</li>)}
      </ul>
    </div>
  );
};

export default FavoritesPanel;
