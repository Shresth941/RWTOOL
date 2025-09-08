// AdminLayout.js
import React from "react";
import Header from "../layout/Header/Header";
import Sidebar from "../layout/Sidebar/Sidebar";

const AdminLayout = ({ children }) => {
  return (
    <div className="admin-layout d-flex" style={{minHeight:"100vh"}}>
      <Sidebar />
      <div className="flex-grow-1">
        <Header />
        <main className="p-4">{children}</main>
      </div>
    </div>
  );
};

export default AdminLayout;
