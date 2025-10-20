import L from "leaflet";
import { GeoSearchControl, OpenStreetMapProvider } from 'leaflet-geosearch';
import "leaflet-geosearch/dist/geosearch.css";
import 'leaflet/dist/leaflet.css';
import { FC, useEffect } from 'react';
import { MapContainer, TileLayer, useMap } from 'react-leaflet';

import markerIcon2x from "leaflet/dist/images/marker-icon-2x.png";
import markerIcon from "leaflet/dist/images/marker-icon.png";
import markerShadow from "leaflet/dist/images/marker-shadow.png";
import { UseStateSetter } from "../utils/useState";

L.Icon.Default.mergeOptions({
    iconRetinaUrl: markerIcon2x,
    iconUrl: markerIcon,
    shadowUrl: markerShadow,
});

const defaultPosition: [number, number] = [52.2324863, 21.0473956];

export interface MapLocationPickerProps {
    address: string | null;
    setAddress: UseStateSetter<string | null>;
    position: { latitude: number; longitude: number } | null;
    setPosition: UseStateSetter<{ latitude: number; longitude: number } | null>;
}

export const MapLocationPicker: FC<MapLocationPickerProps> = ({ address, setAddress, position, setPosition }) => {
    return <MapContainer
        style={{ height: 500, width: 500, zIndex: 0 }}
        zoom={3}
        center={defaultPosition}
    >
        <MapSearchField
            onSelect={(address, [latitude, longitude]) => {
                setAddress(address);
                setPosition({ latitude, longitude });
            }}
        />
        <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
    </MapContainer>
};

export interface MapSearchFieldProps {
    onSelect?: (address: string, position: [number, number]) => void;
}

export const MapSearchField: FC<MapSearchFieldProps> = ({ onSelect }) => {
    const map = useMap();

    const provider = new OpenStreetMapProvider();

    // @ts-ignore
    const searchControl = new GeoSearchControl({
        provider,
        showMarker: true,
    });

    // @ts-ignore
    useEffect(() => {
        map.addControl(searchControl);
        return () => map.removeControl(searchControl);
    }, [map]);

    map.on("geosearch/showlocation", (event: any) => {
        const { label, y, x } = event.location;
        onSelect?.(label, [y, x]);
        console.log("GGGGGGGGG");
    });

    return null;
};