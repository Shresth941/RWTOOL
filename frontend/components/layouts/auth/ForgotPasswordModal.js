// ForgotPasswordModal.js
import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import Nav from "../HomePage/Nav";

const ForgotPasswordModal = ({ show = true, onClose = () => window.history.back() }) => {
  const [email, setEmail] = useState("");
  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Password reset requested for ${email} (stub)`);
    onClose();
  };
  return (
    <>
      <Nav />
      <Modal show={show} onHide={onClose} centered>
        <Modal.Header closeButton>
          <Modal.Title>Forgot Password</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" value={email} onChange={e=>setEmail(e.target.value)} required/>
            </Form.Group>
            <div className="text-end mt-3">
              <Button type="submit" variant="primary">Send Reset Link</Button>
            </div>
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default ForgotPasswordModal;
