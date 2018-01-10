/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_hoja_ruta")
/*  23:    */ public class DetalleHojaRuta
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="detalle_hoja_ruta", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_hoja_ruta")
/*  31:    */   @Column(name="id_detalle_hoja_ruta", unique=true, nullable=false)
/*  32:    */   private int idDetalleHojaRuta;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private int idSucursal;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_hoja_ruta", nullable=true)
/*  41:    */   private HojaRuta hojaRuta;
/*  42:    */   @Column(name="factura", nullable=false, length=20)
/*  43:    */   @Size(max=20)
/*  44: 65 */   private String factura = "";
/*  45:    */   @Column(name="cantidad", nullable=false)
/*  46: 69 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  47:    */   @Column(name="piezas", nullable=false)
/*  48: 72 */   private BigDecimal piezas = BigDecimal.ZERO;
/*  49:    */   @Column(name="bultos", nullable=false)
/*  50: 75 */   private BigDecimal bultos = BigDecimal.ZERO;
/*  51:    */   @Column(name="descripcion", length=200, nullable=true)
/*  52:    */   @Size(max=200)
/*  53: 78 */   private String descripcion = "";
/*  54:    */   @OneToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_despacho_cliente")
/*  56:    */   private DespachoCliente despachoCliente;
/*  57:    */   @Transient
/*  58: 86 */   private boolean seleccionado = false;
/*  59:    */   @Transient
/*  60:    */   private int idDespachoCliente;
/*  61:    */   @Column(name="ciudad", length=200, nullable=true)
/*  62:    */   @Size(max=200)
/*  63: 92 */   private String ciudad = "";
/*  64:    */   
/*  65:    */   public DetalleHojaRuta() {}
/*  66:    */   
/*  67:    */   public DetalleHojaRuta(int idOrganizacion, int idSucursal, DespachoCliente despachoCliente)
/*  68:    */   {
/*  69:104 */     this.idOrganizacion = idOrganizacion;
/*  70:105 */     this.idSucursal = idSucursal;
/*  71:106 */     this.despachoCliente = despachoCliente;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public DetalleHojaRuta(int idOrganizacion, int idSucursal, String factura, BigDecimal cantidad, Long piezas, String descripcion, int idDespachoCliente, Date fechaDespacho, String nombreComercial, String nombreFiscal)
/*  75:    */   {
/*  76:112 */     this.idOrganizacion = idOrganizacion;
/*  77:113 */     this.idSucursal = idSucursal;
/*  78:114 */     this.factura = factura;
/*  79:115 */     this.cantidad = cantidad;
/*  80:116 */     this.piezas = BigDecimal.valueOf(piezas.longValue());
/*  81:117 */     this.descripcion = descripcion;
/*  82:118 */     this.despachoCliente = new DespachoCliente();
/*  83:119 */     this.despachoCliente.setIdDespachoCliente(idDespachoCliente);
/*  84:120 */     this.despachoCliente.setFecha(fechaDespacho);
/*  85:121 */     this.despachoCliente.setEmpresa(new Empresa());
/*  86:122 */     this.despachoCliente.getEmpresa().setNombreComercial(nombreComercial);
/*  87:123 */     this.despachoCliente.getEmpresa().setNombreFiscal(nombreFiscal);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public DetalleHojaRuta(int idOrganizacion, int idSucursal, String factura, BigDecimal cantidad, Long piezas, String descripcion, int idDespachoCliente, Date fechaDespacho, String nombreComercial, String nombreFiscal, String ciudad)
/*  91:    */   {
/*  92:128 */     this(idOrganizacion, idSucursal, factura, cantidad, piezas, descripcion, idDespachoCliente, fechaDespacho, nombreComercial, nombreFiscal);
/*  93:129 */     this.ciudad = ciudad;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdDetalleHojaRuta()
/*  97:    */   {
/*  98:133 */     return this.idDetalleHojaRuta;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdDetalleHojaRuta(int idDetalleHojaRuta)
/* 102:    */   {
/* 103:137 */     this.idDetalleHojaRuta = idDetalleHojaRuta;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdOrganizacion()
/* 107:    */   {
/* 108:141 */     return this.idOrganizacion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdOrganizacion(int idOrganizacion)
/* 112:    */   {
/* 113:145 */     this.idOrganizacion = idOrganizacion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getIdSucursal()
/* 117:    */   {
/* 118:149 */     return this.idSucursal;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setIdSucursal(int idSucursal)
/* 122:    */   {
/* 123:153 */     this.idSucursal = idSucursal;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public HojaRuta getHojaRuta()
/* 127:    */   {
/* 128:157 */     return this.hojaRuta;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setHojaRuta(HojaRuta hojaRuta)
/* 132:    */   {
/* 133:161 */     this.hojaRuta = hojaRuta;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getFactura()
/* 137:    */   {
/* 138:165 */     return this.factura;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setFactura(String factura)
/* 142:    */   {
/* 143:169 */     this.factura = factura;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public BigDecimal getCantidad()
/* 147:    */   {
/* 148:173 */     return this.cantidad;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setCantidad(BigDecimal cantidad)
/* 152:    */   {
/* 153:177 */     this.cantidad = cantidad;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public BigDecimal getPiezas()
/* 157:    */   {
/* 158:181 */     return this.piezas;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setPiezas(BigDecimal piezas)
/* 162:    */   {
/* 163:185 */     this.piezas = piezas;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BigDecimal getBultos()
/* 167:    */   {
/* 168:189 */     return this.bultos;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setBultos(BigDecimal bultos)
/* 172:    */   {
/* 173:193 */     this.bultos = bultos;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getDescripcion()
/* 177:    */   {
/* 178:197 */     return this.descripcion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setDescripcion(String descripcion)
/* 182:    */   {
/* 183:201 */     this.descripcion = descripcion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public DespachoCliente getDespachoCliente()
/* 187:    */   {
/* 188:205 */     return this.despachoCliente;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDespachoCliente(DespachoCliente despachoCliente)
/* 192:    */   {
/* 193:209 */     this.despachoCliente = despachoCliente;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean isSeleccionado()
/* 197:    */   {
/* 198:213 */     return this.seleccionado;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setSeleccionado(boolean seleccionado)
/* 202:    */   {
/* 203:217 */     this.seleccionado = seleccionado;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int getIdDespachoCliente()
/* 207:    */   {
/* 208:221 */     return this.idDespachoCliente;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setIdDespachoCliente(int idDespachoCliente)
/* 212:    */   {
/* 213:225 */     this.idDespachoCliente = idDespachoCliente;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getCiudad()
/* 217:    */   {
/* 218:232 */     return this.ciudad;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setCiudad(String ciudad)
/* 222:    */   {
/* 223:240 */     this.ciudad = ciudad;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public int getId()
/* 227:    */   {
/* 228:250 */     return this.idDetalleHojaRuta;
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleHojaRuta
 * JD-Core Version:    0.7.0.1
 */