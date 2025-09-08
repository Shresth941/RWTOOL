// src/pages/Faq.js
import React, { useState } from "react";
import Header from "../components/layout/Header/Header";
import Footer from "../components/layout/Footer/Footer";
import "../components/Faq/Faq.css";

const defaultFaqs = [
  { question: "What is the RW Tool?", answer: "A secure automated file processing & report management solution." },
  { question: "Who can access it?", answer: "Access is controlled by roles / AD groups assigned by admins." },
  { question: "Which report formats are supported?", answer: "PDF, TXT, CSV and similar formats are supported." },
  { question: "Can I download multiple reports?", answer: "Yes — users can download reports for a period as a ZIP." }
];

export default function Faq() {
  const [openIndex, setOpenIndex] = useState(null);

  return (
    <>
      <Header />
      <div className="faq-page">
        <div className="faq-hero">
          <div className="faq-hero-overlay">
            <h1>Frequently Asked Questions</h1>
            <p>Quick answers to common questions about the RW Tool.</p>
          </div>
        </div>

        <div className="container faq-container py-5">
          <h3 className="faq-title">FREQUENTLY ASKED QUESTIONS</h3>
          <div className="faq-logo mb-3">
            <img src="/assets/images/faq-logo.png" alt="FAQ" width="70" />
          </div>

          {defaultFaqs.map((f, i) => (
            <div className="card faq-card mb-2" key={i} onClick={() => setOpenIndex(openIndex === i ? null : i)}>
              <div className="card-body faq-question d-flex justify-content-between">
                <div>{f.question}</div>
                <div>{openIndex === i ? " UP" : "DOWN"}</div>
              </div>
              {openIndex === i && <div className="card-body faq-answer">{f.answer}</div>}
            </div>
          ))}

        </div>
      </div>

      <Footer />
    </>
  );
}
