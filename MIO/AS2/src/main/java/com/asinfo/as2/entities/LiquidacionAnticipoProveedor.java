/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.OneToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.validation.constraints.Min;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="liquidacion_anticipo_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="id_anticipo_proveedor"), @javax.persistence.Index(columnList="id_documento"), @javax.persistence.Index(columnList="id_asiento")})
/*  30:    */ public class LiquidacionAnticipoProveedor
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="liquidacion_anticipo_proveedor", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="liquidacion_anticipo_proveedor")
/*  37:    */   @Column(name="id_liquidacion_anticipo_proveedor")
/*  38:    */   private int idLiquidacionAnticipoProveedor;
/*  39:    */   @Column(name="id_organizacion", nullable=false)
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   private int idSucursal;
/*  43:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  44:    */   @JoinColumn(name="id_documento", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private Documento documento;
/*  47:    */   @Column(name="numero", nullable=false, length=20)
/*  48:    */   private String numero;
/*  49:    */   @Enumerated(EnumType.ORDINAL)
/*  50:    */   @Column(name="estado", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Estado estado;
/*  53:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  54:    */   @Min(0L)
/*  55:    */   private BigDecimal valor;
/*  56:    */   @OneToOne
/*  57:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  58:    */   private Asiento asiento;
/*  59:    */   @ManyToOne
/*  60:    */   @JoinColumn(name="id_anticipo_proveedor", nullable=false)
/*  61:    */   private AnticipoProveedor anticipoProveedor;
/*  62:    */   @Temporal(TemporalType.DATE)
/*  63:    */   @Column(name="fecha", nullable=false)
/*  64:    */   private Date fecha;
/*  65:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  66:    */   private Date fechaContabilizacion;
/*  67:    */   @Column(name="descripcion", length=200, nullable=true)
/*  68:    */   @Size(max=200)
/*  69:    */   private String descripcion;
/*  70:    */   @OneToMany(mappedBy="liquidacionAnticipoProveedor", fetch=FetchType.LAZY)
/*  71:105 */   private List<DetalleLiquidacionAnticipoProveedor> listaDetalleLiquidacionAnticipoProveedor = new ArrayList();
/*  72:    */   
/*  73:    */   public int getId()
/*  74:    */   {
/*  75:113 */     return this.idLiquidacionAnticipoProveedor;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdLiquidacionAnticipoProveedor()
/*  79:    */   {
/*  80:122 */     return this.idLiquidacionAnticipoProveedor;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdLiquidacionAnticipoProveedor(int idLiquidacionAnticipoProveedor)
/*  84:    */   {
/*  85:133 */     this.idLiquidacionAnticipoProveedor = idLiquidacionAnticipoProveedor;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdOrganizacion()
/*  89:    */   {
/*  90:142 */     return this.idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdOrganizacion(int idOrganizacion)
/*  94:    */   {
/*  95:152 */     this.idOrganizacion = idOrganizacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdSucursal()
/*  99:    */   {
/* 100:161 */     return this.idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdSucursal(int idSucursal)
/* 104:    */   {
/* 105:171 */     this.idSucursal = idSucursal;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Documento getDocumento()
/* 109:    */   {
/* 110:180 */     return this.documento;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDocumento(Documento documento)
/* 114:    */   {
/* 115:190 */     this.documento = documento;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getNumero()
/* 119:    */   {
/* 120:199 */     return this.numero;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setNumero(String numero)
/* 124:    */   {
/* 125:209 */     this.numero = numero;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Asiento getAsiento()
/* 129:    */   {
/* 130:218 */     return this.asiento;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setAsiento(Asiento asiento)
/* 134:    */   {
/* 135:228 */     this.asiento = asiento;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public AnticipoProveedor getAnticipoProveedor()
/* 139:    */   {
/* 140:237 */     return this.anticipoProveedor;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 144:    */   {
/* 145:247 */     this.anticipoProveedor = anticipoProveedor;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Date getFecha()
/* 149:    */   {
/* 150:256 */     return this.fecha;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setFecha(Date fecha)
/* 154:    */   {
/* 155:266 */     this.fecha = fecha;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Date getFechaContabilizacion()
/* 159:    */   {
/* 160:275 */     return this.fechaContabilizacion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 164:    */   {
/* 165:285 */     this.fechaContabilizacion = fechaContabilizacion;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getDescripcion()
/* 169:    */   {
/* 170:294 */     return this.descripcion;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setDescripcion(String descripcion)
/* 174:    */   {
/* 175:304 */     this.descripcion = descripcion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<DetalleLiquidacionAnticipoProveedor> getListaDetalleLiquidacionAnticipoProveedor()
/* 179:    */   {
/* 180:313 */     return this.listaDetalleLiquidacionAnticipoProveedor;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaDetalleLiquidacionAnticipoProveedor(List<DetalleLiquidacionAnticipoProveedor> listaDetalleLiquidacionAnticipoProveedor)
/* 184:    */   {
/* 185:325 */     this.listaDetalleLiquidacionAnticipoProveedor = listaDetalleLiquidacionAnticipoProveedor;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String toString()
/* 189:    */   {
/* 190:330 */     return this.numero;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Estado getEstado()
/* 194:    */   {
/* 195:334 */     return this.estado;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setEstado(Estado estado)
/* 199:    */   {
/* 200:338 */     this.estado = estado;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public BigDecimal getValor()
/* 204:    */   {
/* 205:342 */     return this.valor;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setValor(BigDecimal valor)
/* 209:    */   {
/* 210:346 */     this.valor = valor;
/* 211:    */   }
/* 212:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.LiquidacionAnticipoProveedor
 * JD-Core Version:    0.7.0.1
 */