import React from "react";
import Button from "./MyButton";

const TopBar = () => {
  return (
    <div className="container mt-3">
      <div className="TopBar border border-2 p-3 w-100 d-flex justify-content-center" style={{ backgroundColor: "#543294", color: "#FFFFFF", borderRadius: "10px"}}>
        <h3 style={{fontWeight: "bold"}}>Event Ticketing System</h3>
      </div>
    </div>
  );
};

export default TopBar;
