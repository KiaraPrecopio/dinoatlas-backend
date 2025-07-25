# DinoAtlas - Documentación de Entidades

## 📋 Descripción General del Modelo de Datos

El sistema DinoAtlas maneja información paleontológica de dinosaurios y otros taxones extintos, organizando los datos en múltiples entidades relacionadas para proporcionar una vista completa de cada especie.

## 🏗️ Arquitectura de Entidades

### 1. **Taxon** (Entidad Principal)
**Propósito**: Representa una especie, género, familia u otro rango taxonómico de dinosaurio.

**Campos Principales**:
- `original_id`: Identificador único de la API de Paleobiology Database (ej: "txn:156091")
- `name`: Nombre científico completo (ej: "Tyrannosaurus rex")
- `rank`: Rango taxonómico (SPECIES, GENUS, FAMILY, etc.)
- `parent_id`: Referencia al taxón padre en la jerarquía

**Información Taxonómica**:
- `phylum`, `class_name`, `order_name`, `family`, `genus`: Jerarquía taxonómica completa
- `is_extant`: Indica si la especie está extinta (false) o viva (true)

**Datos Ecológicos**:
- `environment`: Ambiente donde vivía (TERRESTRIAL, MARINE, etc.)
- `motility`: Tipo de movilidad (ACTIVELY_MOBILE, NON_MOBILE, etc.)
- `composition`: Composición esqueletal (ej: "hydroxyapatite")

**Relaciones**:
- Múltiples `TaxonOccurrence` (hallazgos geográficos)
- Múltiples `TaxonImage` (imágenes y reconstrucciones)
- Múltiples `Reference` (referencias bibliográficas)
- Una `TaxonStatistics` (estadísticas calculadas)

---

### 2. **GeologicalInterval**
**Propósito**: Representa períodos geológicos (Jurásico, Cretácico, etc.)

**Funcionalidad**:
- Define rangos temporales con `early_age` y `late_age` en millones de años
- Estructura jerárquica: Era → Período → Época → Edad
- Colores hex para visualización en gráficos

**Ejemplo**: Cretácico Superior (100.5 - 66.0 Ma)

---

### 3. **TaxonOccurrence**
**Propósito**: Registra ubicaciones específicas donde se han encontrado fósiles

**Información Geográfica**:
- `latitude`, `longitude`: Coordenadas exactas del hallazgo
- `country`, `state_province`: Ubicación administrativa
- `formation`: Formación geológica específica

**Información Temporal**:
- `min_age`, `max_age`: Rango de edad del hallazgo
- Relación con `GeologicalInterval`

**Uso**: Permite crear mapas de distribución y análisis biogeográficos

---

### 4. **Reference**
**Propósito**: Almacena referencias bibliográficas de investigaciones paleontológicas

**Campos**:
- `title`, `authors`, `journal`: Información de publicación
- `publication_year`, `volume`, `pages`: Detalles bibliográficos
- `doi`: Identificador digital para acceso directo

**Relaciones**: Vinculada a múltiples `Taxon` y `TaxonOccurrence`

---

### 5. **TaxonImage**
**Propósito**: Gestiona imágenes, reconstrucciones y material visual

**Tipos de Imagen**:
- `RECONSTRUCTION`: Ilustraciones artísticas
- `FOSSIL`: Fotografías de fósiles
- `SKELETON`: Esqueletos montados
- `SKULL`: Cráneos específicos
- `HABITAT`: Reconstrucciones del hábitat

**Metadatos**:
- `caption`, `credit`, `license`: Información de atribución
- `is_primary`: Marca la imagen principal del taxón

---

### 6. **TaxonStatistics**
**Propósito**: Almacena datos calculados y estimaciones físicas

**Estimaciones Físicas**:
- `estimated_length_min/max`: Longitud en metros
- `estimated_weight_min/max`: Peso en kilogramos
- `estimated_height`: Altura en metros

**Información Adicional**:
- `diet_type`: Tipo de dieta (CARNIVORE, HERBIVORE, etc.)
- `discovery_year`: Año del primer descubrimiento
- `first_described_by`: Paleontólogo que describió la especie
- `completeness_percentage`: Qué tan completo está el registro fósil

---

## 🔢 Enumeraciones (Enums)

### TaxonomicRank
```
KINGDOM → PHYLUM → CLASS → ORDER → FAMILY → GENUS → SPECIES → SUBSPECIES
```

### Environment
- **TERRESTRIAL**: Animales que vivían en tierra
- **MARINE**: Ambiente marino
- **FRESHWATER**: Agua dulce
- **BRACKISH**: Agua salobre

### Motility
- **ACTIVELY_MOBILE**: Se movían activamente
- **PASSIVELY_MOBILE**: Movimiento pasivo (ej: corrientes)
- **NON_MOBILE**: Sésiles (no se movían)
- **FACULTATIVELY_MOBILE**: Movimiento opcional

### DietType
- **CARNIVORE**: Comedores de carne
- **HERBIVORE**: Comedores de plantas
- **OMNIVORE**: Dieta mixta
- **PISCIVORE**: Comedores de peces
- **INSECTIVORE**: Comedores de insectos

### ImageType
- **RECONSTRUCTION**: Reconstrucciones artísticas
- **FOSSIL**: Fósiles reales
- **SKELETON**: Esqueletos completos
- **SKULL**: Cráneos
- **HABITAT**: Reconstrucciones del hábitat
- **SIZE_COMPARISON**: Comparaciones de tamaño

---

## 🔄 Flujo de Datos

1. **Sincronización**: `PaleobiologyApiService` obtiene datos de la API
2. **Procesamiento**: `TaxonService` mapea y guarda los `Taxon`
3. **Enriquecimiento**: Se agregan `TaxonOccurrence`, `TaxonImage`, `Reference`
4. **Cálculo**: Se generan `TaxonStatistics` basadas en los datos
5. **Consulta**: Los usuarios acceden via `TaxonController`

---

## 🎯 Casos de Uso Principales

### Búsqueda de Especies
```sql
SELECT * FROM taxon 
WHERE rank = 'SPECIES' 
AND name LIKE '%rex%'
```

### Análisis Temporal
```sql
SELECT t.name, gi.name as period 
FROM taxon t 
JOIN geological_interval gi ON t.earliest_interval_id = gi.id
WHERE gi.early_age BETWEEN 145 AND 100
```

### Distribución Geográfica
```sql
SELECT t.name, to.country, to.latitude, to.longitude
FROM taxon t 
JOIN taxon_occurrence to ON t.id = to.taxon_id
WHERE t.family = 'Tyrannosauridae'
```

### Estadísticas Físicas
```sql
SELECT t.name, ts.estimated_length_max, ts.diet_type
FROM taxon t 
JOIN taxon_statistics ts ON t.id = ts.taxon_id
ORDER BY ts.estimated_length_max DESC
```

---

## 📊 Métricas del Sistema

- **Taxones**: ~1000+ especies de dinosaurios
- **Occurrencias**: Miles de hallazgos geográficos
- **Referencias**: Cientos de papers científicos
- **Imágenes**: Reconstrucciones y fósiles
- **Intervalos**: ~500 períodos geológicos

---

## 🔧 Consideraciones Técnicas

### Indexación
- Índices en campos de búsqueda frecuente (`name`, `rank`, `genus`)
- Índices compuestos para consultas complejas
- Índices geográficos para `latitude`/`longitude`

### Performance
- Lazy loading en relaciones OneToMany
- Fetch joins para consultas específicas
- Cache en consultas frecuentes

### Integridad
- Constraints de foreign key
- Validaciones en enums
- Campos nullable apropiados

Esta arquitectura permite análisis paleontológicos complejos mientras mantiene la flexibilidad para agregar nuevos tipos de datos científicos.