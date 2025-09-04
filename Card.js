import React from 'react';

/**
 * A reusable Card component that can display a header and content.
 * @param {object} props - The component's props.
 * @param {React.ReactNode} props.header - The content to be rendered in the card header.
 * @param {React.ReactNode} props.children - The content to be rendered in the card body.
 */
const Card = ({ header, children }) => {
    return (
        <div className="card">
            {/* This is the crucial part: It checks if a header was provided.
                If it was, it renders it inside a div with the correct Bootstrap class. */}
            {header && (
                <div className="card-header">
                    {header}
                </div>
            )}
            <div className="card-body">
                {children}
            </div>
        </div>
    );
};

export default Card;
