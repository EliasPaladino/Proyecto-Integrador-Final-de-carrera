import React, { useState } from "react";
import {
  FaShareAlt,
  FaFacebook,
  FaLinkedinIn,
  FaInstagram,
  FaTwitter,
  FaRegHeart,
} from "react-icons/fa";
import { useParams } from "react-router-dom";
import "./share.css";

export default function ProductShare({ url }) {
  const { id } = useParams();
  const [state, setState] = useState(false);
  function onClick() {
    setState(!state);
  }
  return (
    
    <div className="share-component container">
      <FaRegHeart className="heart-icon"/>
      <FaShareAlt className="share-icon" onClick={onClick} />
      <div
        className="share-links"
        style={{
          transition: "width 0.3s",
          width: !state ? "0" : "80%",
        }}
      >
        <ShareLinks id={id}/>
      </div>
    </div>
  );
}

const ShareLinks = ({id}) => (
  <>
    <a
      href={"https://www.facebook.com/sharer/sharer.php?u=http://grupo6c5frontb.s3-website.us-east-2.amazonaws.com/producto/"+id}
      target="_blank"
    >
      <FaFacebook />
    </a>
    <a
      href={"https://www.linkedin.com/sharing/share-offsite/?url=https://www.instagram.com/digitalbooking6/"}
      target="_blank"
    >
      <FaLinkedinIn />
    </a>
    <a
      href={"https://twitter.com/intent/tweet?text=http://grupo6c5frontb.s3-website.us-east-2.amazonaws.com/producto/"+id}
      target="_blank"
    >
      <FaTwitter />
    </a>
  </>
);
