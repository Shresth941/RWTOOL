// PaginationControls.js
import React from "react";

const PaginationControls = ({ page, size, total, onPageChange }) => {
  const totalPages = Math.ceil(total / size) || 1;
  return (
    <nav>
      <ul className="pagination">
        <li className={"page-item " + (page <= 0 ? "disabled" : "")}>
          <button className="page-link" onClick={() => onPageChange(Math.max(0, page - 1))}>Prev</button>
        </li>
        <li className="page-item disabled">
          <span className="page-link">{page + 1} / {totalPages}</span>
        </li>
        <li className={"page-item " + (page + 1 >= totalPages ? "disabled" : "")}>
          <button className="page-link" onClick={() => onPageChange(Math.min(totalPages - 1, page + 1))}>Next</button>
        </li>
      </ul>
    </nav>
  );
};

export default PaginationControls;
