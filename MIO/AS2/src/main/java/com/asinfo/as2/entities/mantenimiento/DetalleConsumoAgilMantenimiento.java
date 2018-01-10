/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.entities.Lote;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.DecimalMin;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="detalle_consumo_agil_mantenimiento")
/*  25:    */ public class DetalleConsumoAgilMantenimiento
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="detalle_consumo_agil_mantenimiento", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_consumo_agil_mantenimiento")
/*  32:    */   @Column(name="id_detalle_consumo_agil_mantenimiento")
/*  33:    */   private int idDetalleConsumoAgilMantenimiento;
/*  34:    */   @Column(name="id_organizacion")
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal")
/*  37:    */   private int idSucursal;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_equipo", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private Equipo equipo;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_componente_equipo", nullable=true)
/*  44:    */   private ComponenteEquipo componenteEquipo;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_actividad_mantenimiento", nullable=true)
/*  47:    */   private ActividadMantenimiento actividadMantenimiento;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_material", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private Producto material;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_bodega_origen", nullable=true)
/*  54:    */   private Bodega bodegaOrigen;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_lote")
/*  57:    */   private Lote lote;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_destino_costo", nullable=true)
/*  60:    */   private DestinoCosto destinoCosto;
/*  61:    */   @Column(name="cantidad", precision=12, scale=2, nullable=true)
/*  62:    */   @NotNull
/*  63:    */   @DecimalMin("0.01")
/*  64: 75 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_consumo_agil_mantenimiento", nullable=false)
/*  67:    */   @NotNull
/*  68:    */   private ConsumoAgilMantenimiento consumoAgilMantenimiento;
/*  69:    */   @Transient
/*  70:    */   private BigDecimal saldo;
/*  71:    */   
/*  72:    */   public int getId()
/*  73:    */   {
/*  74: 90 */     return this.idDetalleConsumoAgilMantenimiento;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdDetalleConsumoAgilMantenimiento()
/*  78:    */   {
/*  79: 94 */     return this.idDetalleConsumoAgilMantenimiento;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdDetalleConsumoAgilMantenimiento(int idDetalleConsumoAgilMantenimiento)
/*  83:    */   {
/*  84: 98 */     this.idDetalleConsumoAgilMantenimiento = idDetalleConsumoAgilMantenimiento;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdOrganizacion()
/*  88:    */   {
/*  89:102 */     return this.idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdOrganizacion(int idOrganizacion)
/*  93:    */   {
/*  94:106 */     this.idOrganizacion = idOrganizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getIdSucursal()
/*  98:    */   {
/*  99:110 */     return this.idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setIdSucursal(int idSucursal)
/* 103:    */   {
/* 104:114 */     this.idSucursal = idSucursal;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Equipo getEquipo()
/* 108:    */   {
/* 109:118 */     return this.equipo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setEquipo(Equipo equipo)
/* 113:    */   {
/* 114:122 */     this.equipo = equipo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ComponenteEquipo getComponenteEquipo()
/* 118:    */   {
/* 119:126 */     return this.componenteEquipo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setComponenteEquipo(ComponenteEquipo componenteEquipo)
/* 123:    */   {
/* 124:130 */     this.componenteEquipo = componenteEquipo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public ActividadMantenimiento getActividadMantenimiento()
/* 128:    */   {
/* 129:134 */     return this.actividadMantenimiento;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setActividadMantenimiento(ActividadMantenimiento actividadMantenimiento)
/* 133:    */   {
/* 134:138 */     this.actividadMantenimiento = actividadMantenimiento;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Producto getMaterial()
/* 138:    */   {
/* 139:142 */     return this.material;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setMaterial(Producto material)
/* 143:    */   {
/* 144:146 */     this.material = material;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Bodega getBodegaOrigen()
/* 148:    */   {
/* 149:150 */     return this.bodegaOrigen;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setBodegaOrigen(Bodega bodegaOrigen)
/* 153:    */   {
/* 154:154 */     this.bodegaOrigen = bodegaOrigen;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Lote getLote()
/* 158:    */   {
/* 159:158 */     return this.lote;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setLote(Lote lote)
/* 163:    */   {
/* 164:162 */     this.lote = lote;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public DestinoCosto getDestinoCosto()
/* 168:    */   {
/* 169:166 */     return this.destinoCosto;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setDestinoCosto(DestinoCosto destinoCosto)
/* 173:    */   {
/* 174:170 */     this.destinoCosto = destinoCosto;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public BigDecimal getCantidad()
/* 178:    */   {
/* 179:174 */     return this.cantidad;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setCantidad(BigDecimal cantidad)
/* 183:    */   {
/* 184:178 */     this.cantidad = cantidad;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public ConsumoAgilMantenimiento getConsumoAgilMantenimiento()
/* 188:    */   {
/* 189:182 */     return this.consumoAgilMantenimiento;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setConsumoAgilMantenimiento(ConsumoAgilMantenimiento consumoAgilMantenimiento)
/* 193:    */   {
/* 194:186 */     this.consumoAgilMantenimiento = consumoAgilMantenimiento;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public BigDecimal getSaldo()
/* 198:    */   {
/* 199:190 */     return this.saldo;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setSaldo(BigDecimal saldo)
/* 203:    */   {
/* 204:194 */     this.saldo = saldo;
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.DetalleConsumoAgilMantenimiento
 * JD-Core Version:    0.7.0.1
 */