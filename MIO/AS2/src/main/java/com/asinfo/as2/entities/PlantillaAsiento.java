/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.OrderBy;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="plantilla_asiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  25:    */ public class PlantillaAsiento
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="plantilla_asiento", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plantilla_asiento")
/*  33:    */   @Column(name="id_plantilla_asiento")
/*  34:    */   private int idPlantillaAsiento;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  39:    */   private Sucursal sucursal;
/*  40:    */   @NotNull
/*  41:    */   @Size(max=10)
/*  42:    */   @Column(name="codigo", nullable=false, length=10)
/*  43:    */   private String codigo;
/*  44:    */   @NotNull
/*  45:    */   @Size(max=50)
/*  46:    */   @Column(name="nombre", nullable=false, length=50)
/*  47:    */   private String nombre;
/*  48:    */   @Column(name="indicador_porcentaje", nullable=false)
/*  49:    */   private boolean indicadorPorcentaje;
/*  50:    */   @Column(name="descripcion", length=200)
/*  51:    */   @Size(max=200)
/*  52:    */   private String descripcion;
/*  53:    */   @Column(name="activo", nullable=false)
/*  54:    */   private boolean activo;
/*  55:    */   @Column(name="predeterminado", nullable=false)
/*  56:    */   private boolean predeterminado;
/*  57:    */   @OneToMany(mappedBy="plantillaAsiento", fetch=FetchType.LAZY)
/*  58:    */   @OrderBy("idDetallePlantillaAsiento")
/*  59: 83 */   private List<DetallePlantillaAsiento> listaDetallePlantillaAsiento = new ArrayList();
/*  60:    */   @Transient
/*  61:    */   private BigDecimal totalDebe;
/*  62:    */   @Transient
/*  63:    */   private BigDecimal totalHaber;
/*  64:    */   
/*  65:    */   public int getId()
/*  66:    */   {
/*  67: 98 */     return this.idPlantillaAsiento;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdPlantillaAsiento()
/*  71:    */   {
/*  72:107 */     return this.idPlantillaAsiento;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdPlantillaAsiento(int idPlantillaAsiento)
/*  76:    */   {
/*  77:117 */     this.idPlantillaAsiento = idPlantillaAsiento;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82:126 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87:136 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Sucursal getSucursal()
/*  91:    */   {
/*  92:145 */     return this.sucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setSucursal(Sucursal sucursal)
/*  96:    */   {
/*  97:155 */     this.sucursal = sucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getCodigo()
/* 101:    */   {
/* 102:164 */     return this.codigo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setCodigo(String codigo)
/* 106:    */   {
/* 107:174 */     this.codigo = codigo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getNombre()
/* 111:    */   {
/* 112:183 */     return this.nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setNombre(String nombre)
/* 116:    */   {
/* 117:193 */     this.nombre = nombre;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isIndicadorPorcentaje()
/* 121:    */   {
/* 122:202 */     return this.indicadorPorcentaje;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIndicadorPorcentaje(boolean indicadorPorcentaje)
/* 126:    */   {
/* 127:212 */     this.indicadorPorcentaje = indicadorPorcentaje;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<DetallePlantillaAsiento> getListaDetallePlantillaAsiento()
/* 131:    */   {
/* 132:221 */     return this.listaDetallePlantillaAsiento;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setListaDetallePlantillaAsiento(List<DetallePlantillaAsiento> listaDetallePlantillaAsiento)
/* 136:    */   {
/* 137:231 */     this.listaDetallePlantillaAsiento = listaDetallePlantillaAsiento;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public BigDecimal getTotalDebe()
/* 141:    */   {
/* 142:235 */     return this.totalDebe;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setTotalDebe(BigDecimal totalDebe)
/* 146:    */   {
/* 147:239 */     this.totalDebe = totalDebe;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getTotalHaber()
/* 151:    */   {
/* 152:243 */     return this.totalHaber;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setTotalHaber(BigDecimal totalHaber)
/* 156:    */   {
/* 157:247 */     this.totalHaber = totalHaber;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getDescripcion()
/* 161:    */   {
/* 162:251 */     return this.descripcion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDescripcion(String descripcion)
/* 166:    */   {
/* 167:255 */     this.descripcion = descripcion;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean isActivo()
/* 171:    */   {
/* 172:259 */     return this.activo;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setActivo(boolean activo)
/* 176:    */   {
/* 177:263 */     this.activo = activo;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isPredeterminado()
/* 181:    */   {
/* 182:267 */     return this.predeterminado;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setPredeterminado(boolean predeterminado)
/* 186:    */   {
/* 187:271 */     this.predeterminado = predeterminado;
/* 188:    */   }
/* 189:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PlantillaAsiento
 * JD-Core Version:    0.7.0.1
 */