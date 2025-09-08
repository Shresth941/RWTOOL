// src/pages/ContactUs.js
import React, { useState } from "react";
import Header from "../components/layout/Header/Header";
import Footer from "../components/layout/Footer/Footer";
import { useNavigate } from "react-router-dom";
import "../components/ContactUs/ContactUs.css";

export default function ContactUs() {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    // For training/demo: we just show an alert. If you have a backend contact endpoint, call it here using axios.
    alert("Thanks! Your message has been submitted.");
    navigate("/");
  };

  return (
    <>
      <Header />
      <div className="contact-page">
        <div className="contact-hero">
          <div className="contact-hero-overlay">
            <h1>We'd love to hear from you</h1>
            <p>Reach out for support, questions or demo requests.</p>
          </div>
        </div>

        <div className="container py-5 contact-container">
          <h2>Get in touch with us</h2>
          <p className="text-muted">Our team is here to assist you.</p>

          <form onSubmit={handleSubmit} style={{ maxWidth: 900 }}>
            <div className="row mb-3">
              <div className="col-md-4">
                <label className="form-label">Your Name</label>
                <input className="form-control" required value={name} onChange={e => setName(e.target.value)} />
              </div>
              <div className="col-md-4">
                <label className="form-label">Email</label>
                <input type="email" className="form-control" required value={email} onChange={e => setEmail(e.target.value)} />
              </div>
              <div className="col-md-4">
                <label className="form-label">Phone (optional)</label>
                <input className="form-control" />
              </div>
            </div>

            <div className="mb-3">
              <label className="form-label">Message</label>
              <textarea className="form-control" rows="5" required value={message} onChange={e => setMessage(e.target.value)} />
            </div>

            <button type="submit" className="btn btn-primary">Send Message</button>
          </form>
        </div>
      </div>

      <Footer />
    </>
  );
}
