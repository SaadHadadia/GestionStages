import React from 'react';
import StageInfos from './evaluerStage/StageInfos';

const EvaluerStage: React.FC = () => {
  return(
    <div className="flex h-screen m-20 justify-center">
      <div className="bg-white shadow-lg rounded-lg p-8 w-full">
        <StageInfos description={''} setDescription={function (value: React.SetStateAction<string>): void {
          throw new Error('Function not implemented.');
        } } objectif={''} setObjectif={function (value: React.SetStateAction<string>): void {
          throw new Error('Function not implemented.');
        } }/>
      </div>
    </div>
);}

export default EvaluerStage;