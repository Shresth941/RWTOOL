// components/admin/DashboardPage.js
import React from "react";
import Card from "../Card/Card";

export default function DashboardPage() {
  return (
    <div className="page-content p-3">
      <Card headerContent={<h5>Operations Dashboard</h5>}>
        <div className="row">
          <div className="col-md-4">
            <div className="p-3 border rounded">
              <h6>Pending Ingests</h6>
              <p className="display-6">3</p>
            </div>
          </div>
          <div className="col-md-4">
            <div className="p-3 border rounded">
              <h6>Recent Downloads</h6>
              <p className="m-0">12 files</p>
            </div>
          </div>
          <div className="col-md-4">
            <div className="p-3 border rounded">
              <h6>Active Users</h6>
              <p className="m-0">28</p>
            </div>
          </div>
        </div>
      </Card>
    </div>
  );
}

