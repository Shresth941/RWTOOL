// NotificationsPanel.js
import React, { useEffect, useState } from "react";
import notificationsService from "../../services/notificationsService";

const NotificationsPanel = ({ userId }) => {
  const [notes, setNotes] = useState([]);

  useEffect(()=> {
    if (!userId) return;
    notificationsService.getByUser(userId).then(res => setNotes(res.data || res)).catch(()=>{});
  }, [userId]);

  const markRead = async (id) => {
    try {
      await notificationsService.markRead(id);
      setNotes(prev => prev.map(n => n.id === id ? {...n, read:true} : n));
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <h5>Notifications</h5>
      <ul className="list-group">
        {notes.map(n => (
          <li className="list-group-item d-flex justify-content-between" key={n.id}>
            <div>
              <div style={{fontWeight:600}}>{n.title}</div>
              <div className="text-muted">{n.message}</div>
            </div>
            <div>
              {!n.read && <button className="btn btn-sm btn-primary" onClick={()=>markRead(n.id)}>Mark read</button>}
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default NotificationsPanel;
