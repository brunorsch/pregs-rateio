import "./index.scss";

import { iconsMap } from "../../utils/iconMap";

export type TCard = {
  icon: keyof typeof iconsMap;
  title: string;
  description: string;
};

// TO DO: Puxar o card do bootstrap e adaptar para nossos estilos (tava com pregssssssssssssssss)

export const Card = ({ icon, title, description }: TCard) => {
  const IconComponent = iconsMap[icon];

  return (
    <div className="feature-card">
      <div className="icon">
        <IconComponent size={40} color="#F1E3FF" />
      </div>
      <h5>{title}</h5>
      <p>{description}</p>
    </div>
  );
};
