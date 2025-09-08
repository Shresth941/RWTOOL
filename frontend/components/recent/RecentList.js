// RecentList.js
import React, { useEffect, useState } from "react";
import userReportsService from "../../services/userReportsService";

const RecentList = ({ userId }) => {
  const [recent, setRecent] = useState([]);

  useEffect(()=> {
    if (!userId) return;
    userReportsService.getRecent(userId).then(res => setRecent(res.data || res)).catch(()=>{});
  }, [userId]);

  return (
    <div>
      <h5>Recently Accessed</h5>
      <ul className="list-group">
        {recent.map(r => <li key={r.id} className="list-group-item">{r.reportName || r.report?.name}</li>)}
      </ul>
    </div>
  );
};

export default RecentList;
