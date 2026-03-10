"use client";

import { useState } from "react";
import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import L from "leaflet";

import "leaflet/dist/leaflet.css";

L.Icon.Default.mergeOptions({
    iconRetinaUrl: "/leaflet_markers/marker-icon-2x.png",
    iconUrl: "/leaflet_markers/marker-icon.png",
    shadowUrl: "/leaflet_markers/marker-shadow.png",
});

export default function MapPicker(
    /**
     * @ignore
     */
    { onLocationSelected }: { onLocationSelected: (lat: number, lon: number) => void }) {
    const [position, setPosition] = useState<[number, number] | null>(null);

    function LocationMarker() {
        useMapEvents({
            click(e) {
                const { lat, lng } = e.latlng;
                setPosition([lat, lng]);
                onLocationSelected(lat, lng);
            },
        });

        return position === null ? null : <Marker position={position} />;
    }

    return (
        <MapContainer
            center={[52.0, 19.0]}
            zoom={5}
            className="w-full h-[400px] aspect-video rounded-lg shadow-lg mb-6"
        >
            <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <LocationMarker />
        </MapContainer>
    );
}
