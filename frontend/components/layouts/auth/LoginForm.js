// LoginForm.js
import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../../services/authService";
import { AuthContext } from "../../context/AuthContext";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const data = await authService.login({ username: email, password });
      login(data); // context stores token/user
      navigate("/userlogin");
    } catch (err) {
      alert(err?.response?.data?.message || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container" style={{maxWidth:520}}>
      <h3 className="mb-3">Login</h3>
      <form onSubmit={handleSubmit}>
        <div className="mb-2">
          <input className="form-control" placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} required/>
        </div>
        <div className="mb-3">
          <input type="password" className="form-control" placeholder="Password" value={password} onChange={e=>setPassword(e.target.value)} required/>
        </div>
        <div className="d-flex gap-2">
          <button className="btn btn-primary" type="submit" disabled={loading}>{loading ? "Signing..." : "Sign in"}</button>
        </div>
      </form>
    </div>
  );
};

export default LoginForm;
