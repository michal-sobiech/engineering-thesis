import L from "leaflet";
import { GeoSearchControl, OpenStreetMapProvider } from 'leaflet-geosearch';
import "leaflet-geosearch/dist/geosearch.css";
import 'leaflet/dist/leaflet.css';
import { FC, useEffect, useRef } from 'react';
import { MapContainer, TileLayer, useMap } from 'react-leaflet';

import markerIcon2x from "leaflet/dist/images/marker-icon-2x.png";
import markerIcon from "leaflet/dist/images/marker-icon.png";
import markerShadow from "leaflet/dist/images/marker-shadow.png";
import { UseStateSetter } from "../utils/use-state";

L.Icon.Default.mergeOptions({
    iconRetinaUrl: markerIcon2x,
    iconUrl: markerIcon,
    shadowUrl: markerShadow,
});

const defaultPosition: [number, number] = [52.2324863, 21.0473956];
const DEFAULT_ZOOM = 12;

export interface MapLocationPickerProps {
    address: string | null;
    setAddress: UseStateSetter<string | null>;
    position: { latitude: number; longitude: number } | null;
    setPosition: UseStateSetter<{ latitude: number; longitude: number } | null>;
}

export const MapLocationPicker: FC<MapLocationPickerProps> = ({ address, setAddress, setPosition }) => {
    return <MapContainer
        style={{ minHeight: 300, width: "100%", zIndex: 0 }}
        zoom={3}
        center={defaultPosition}
    >
        <MapSearchField
            address={address}
            onSelect={(address, [latitude, longitude]) => {
                setAddress(address);
                setPosition({ latitude, longitude });
            }}
        />
        <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
    </MapContainer>
};

export interface MapSearchFieldProps {
    address: string | null;
    onSelect?: (address: string, position: [number, number]) => void;
}

export const MapSearchField: FC<MapSearchFieldProps> = ({ address, onSelect }) => {
    const map = useMap();
    const loadedInitialAddressRef = useRef<boolean>(false);

    const provider = new OpenStreetMapProvider();

    // @ts-ignore
    const searchControl = new GeoSearchControl({
        provider,
        showMarker: true,
    });

    useEffect(() => {
        if (address === null || loadedInitialAddressRef.current === true) {
            return
        }
        loadedInitialAddressRef.current = true;

        provider.search({ query: address })
            .then(results => {
                const firstRow = results.at(0);
                console.log(firstRow);
                if (firstRow === undefined) {
                    return;
                }

                const { label, x, y } = firstRow;
                map.setView([y, x], DEFAULT_ZOOM);
                onSelect?.(label, [y, x]);
            });
    }, [address]);

    // @ts-ignore
    useEffect(() => {
        map.addControl(searchControl);
        return () => map.removeControl(searchControl);
    }, [map]);

    map.on("geosearch/showlocation", (event: any) => {
        const { label, y, x } = event.location;
        onSelect?.(label, [y, x]);
    });

    return null;
};