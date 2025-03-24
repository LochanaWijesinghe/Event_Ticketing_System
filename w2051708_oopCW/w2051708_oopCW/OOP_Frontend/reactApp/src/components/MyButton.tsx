import React from "react";

interface Props {
  text: string;
  link: string;
}

const buttton = ({ text, link }: Props) => {
  return (
    <div>
      <button
        type="button"
        className={`btn ${text === "Start" ? "btn-success" : "btn-danger"}`}
        onClick={() => (window.location.href = link)}
      >
        {text}
      </button>
    </div>
  );
};

export default buttton;
