package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LifeHabit {
    BORING("boring"),
    INFAUNAL("infaunal"),
    SHALLOW_INFAUNAL("shallow infaunal"),
    DEEP_INFAUNAL("deep infaunal"),
    SEMI_INFAUNAL("semi-infaunal"),
    EPIFAUNAL("epifaunal"),
    LOW_LEVEL_EPIFAUNAL("low-level epifaunal"),
    INTERMEDIATE_LEVEL_EPIFAUNAL("intermediate-level epifaunal"),
    UPPER_LEVEL_EPIFAUNAL("upper-level epifaunal"),
    NEKTOBENTHIC("nektobenthic"),
    NEKTONIC("nektonic"),
    PLANKTONIC("planktonic"),
    FOSSORIAL("fossorial"),
    SEMIFOSSORIAL("semifossorial"),
    GROUND_DWELLING("ground dwelling"),
    CURSORIAL("cursorial"),
    SALTATORIAL("saltatorial"),
    SCANSORIAL("scansorial"),
    ARBOREAL("arboreal"),
    GLIDING("gliding"),
    VOLANT("volant"),
    AMPHIBIOUS("amphibious"),
    HERBACEOUS("herbaceous"),
    ARBORESCENT("arborescent"),
    AQUATIC("aquatic"),
    COLONIAL("colonial"),
    GREGARIOUS("gregarious"),
    SOLITARY("solitary"),
    CLONAL("clonal"),
    POLYMORPH("polymorph"),
    DEPTH_SURFACE("depth=surface"),
    DEPTH_THERMOCLINE("depth=thermocline"),
    DEPTH_SUBTHERMOCLINE("depth=subthermocline"),
    DEPTH_DEEP("depth=deep"),
    UNKNOWN("unknown");

    private final String value;
}
