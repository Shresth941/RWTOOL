// ReportUpload.js
import React, { useState } from "react";
import ingestService from "../../services/ingestService";

const ReportUpload = ({ onUploaded }) => {
  const [file, setFile] = useState(null);
  const [metadata, setMetadata] = useState({ uniqueId: "", action: "New", fileType: "PDF", outputFolderPath: "" });
  const [uploading, setUploading] = useState(false);

  const submit = async () => {
    if (!file) return alert("Select file");
    setUploading(true);
    try {
      const form = new FormData();
      form.append("file", file);
      Object.keys(metadata).forEach(k => form.append(k, metadata[k]));
      await ingestService.upload(form);
      alert("Uploaded");
      setFile(null);
      onUploaded?.();
    } catch (err) {
      console.error(err);
      alert("Upload failed");
    } finally { setUploading(false); }
  };

  return (
    <div>
      <h5>Manual Upload</h5>
      <div className="mb-2"><input type="file" onChange={(e)=>setFile(e.target.files[0])} /></div>
      <div className="row">
        <div className="col"><input className="form-control" placeholder="UniqueId" value={metadata.uniqueId} onChange={e=>setMetadata({...metadata, uniqueId:e.target.value})} /></div>
        <div className="col"><input className="form-control" placeholder="Output path" value={metadata.outputFolderPath} onChange={e=>setMetadata({...metadata, outputFolderPath:e.target.value})} /></div>
      </div>
      <div className="mt-2">
        <button className="btn btn-success" onClick={submit} disabled={uploading}>{uploading ? "Uploading..." : "Upload"}</button>
      </div>
    </div>
  );
};

export default ReportUpload;
