// UsersPage.js
import React, { useEffect, useState } from "react";
import usersService from "../../services/usersService";
import LoadingSpinner from "../common/LoadingSpinner";

const UsersPage = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(()=> {
    const load = async () => {
      try {
        const res = await usersService.getAll();
        setUsers(res.data || res);
      } catch (err) {
        console.error(err);
        alert("Failed to load users");
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <h3>Manage Users</h3>
      <table className="table">
        <thead><tr><th>#</th><th>Name</th><th>Email</th><th>Group</th></tr></thead>
        <tbody>
          {users.map((u, idx) => (
            <tr key={u.id || idx}>
              <td>{idx+1}</td>
              <td>{u.fullname}</td>
              <td>{u.email}</td>
              <td>{u.group}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UsersPage;
