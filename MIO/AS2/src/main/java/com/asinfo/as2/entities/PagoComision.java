/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import com.asinfo.as2.enumeraciones.Mes;
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
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="pago_comision", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero"})})
/*  28:    */ public class PagoComision
/*  29:    */   extends EntidadBase
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="pago_comision", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pago_comision")
/*  35:    */   @Column(name="id_pago_comision")
/*  36:    */   private int idPagoComision;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=false)
/*  40:    */   private int idSucursal;
/*  41:    */   @Column(name="numero", nullable=false, length=20)
/*  42:    */   @NotNull
/*  43:    */   @Size(max=20)
/*  44:    */   private String numero;
/*  45:    */   @Column(name="mes_inicial", nullable=false)
/*  46:    */   @Enumerated(EnumType.ORDINAL)
/*  47:    */   @NotNull
/*  48:    */   private Mes mesInicial;
/*  49:    */   @Column(name="mes_final", nullable=false)
/*  50:    */   @Enumerated(EnumType.ORDINAL)
/*  51:    */   @NotNull
/*  52:    */   private Mes mesFinal;
/*  53:    */   @Column(name="anio_inicial", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   private int anioInicial;
/*  56:    */   @Column(name="anio_final", nullable=false)
/*  57:    */   @NotNull
/*  58:    */   private int anioFinal;
/*  59:    */   @Temporal(TemporalType.DATE)
/*  60:    */   @Column(name="fecha_desde", nullable=false)
/*  61:    */   @NotNull
/*  62:    */   private Date fechaDesde;
/*  63:    */   @Temporal(TemporalType.DATE)
/*  64:    */   @Column(name="fecha_hasta", nullable=false)
/*  65:    */   @NotNull
/*  66:    */   private Date fechaHasta;
/*  67:    */   @Column(name="estado", nullable=false)
/*  68:    */   @Enumerated(EnumType.ORDINAL)
/*  69:    */   private Estado estado;
/*  70:    */   @Temporal(TemporalType.DATE)
/*  71:    */   @Column(name="fecha", nullable=false)
/*  72:    */   @NotNull
/*  73:    */   private Date fecha;
/*  74:    */   @Column(name="descripcion", length=200)
/*  75:    */   @Size(max=200)
/*  76:    */   private String descripcion;
/*  77:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  78:    */   @JoinColumn(name="id_documento", nullable=false)
/*  79:    */   private Documento documento;
/*  80:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="pagoComision")
/*  81:119 */   private List<DetallePagoComision> listaDetallePagoComision = new ArrayList();
/*  82:    */   
/*  83:    */   public int getId()
/*  84:    */   {
/*  85:135 */     return this.idPagoComision;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdOrganizacion()
/*  89:    */   {
/*  90:139 */     return this.idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdOrganizacion(int idOrganizacion)
/*  94:    */   {
/*  95:143 */     this.idOrganizacion = idOrganizacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdSucursal()
/*  99:    */   {
/* 100:147 */     return this.idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdSucursal(int idSucursal)
/* 104:    */   {
/* 105:151 */     this.idSucursal = idSucursal;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Estado getEstado()
/* 109:    */   {
/* 110:155 */     return this.estado;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setEstado(Estado estado)
/* 114:    */   {
/* 115:159 */     this.estado = estado;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Date getFecha()
/* 119:    */   {
/* 120:163 */     return this.fecha;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setFecha(Date fecha)
/* 124:    */   {
/* 125:167 */     this.fecha = fecha;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Documento getDocumento()
/* 129:    */   {
/* 130:171 */     return this.documento;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setDocumento(Documento documento)
/* 134:    */   {
/* 135:175 */     this.documento = documento;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List<DetallePagoComision> getListaDetallePagoComision()
/* 139:    */   {
/* 140:179 */     return this.listaDetallePagoComision;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setListaDetallePagoComision(List<DetallePagoComision> listaDetallePagoComision)
/* 144:    */   {
/* 145:183 */     this.listaDetallePagoComision = listaDetallePagoComision;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getDescripcion()
/* 149:    */   {
/* 150:187 */     return this.descripcion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setDescripcion(String descripcion)
/* 154:    */   {
/* 155:191 */     this.descripcion = descripcion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getIdPagoComision()
/* 159:    */   {
/* 160:195 */     return this.idPagoComision;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIdPagoComision(int idPagoComision)
/* 164:    */   {
/* 165:199 */     this.idPagoComision = idPagoComision;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getNumero()
/* 169:    */   {
/* 170:203 */     return this.numero;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setNumero(String numero)
/* 174:    */   {
/* 175:207 */     this.numero = numero;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Mes getMesInicial()
/* 179:    */   {
/* 180:211 */     return this.mesInicial;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setMesInicial(Mes mesInicial)
/* 184:    */   {
/* 185:215 */     this.mesInicial = mesInicial;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Mes getMesFinal()
/* 189:    */   {
/* 190:219 */     return this.mesFinal;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setMesFinal(Mes mesFinal)
/* 194:    */   {
/* 195:223 */     this.mesFinal = mesFinal;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int getAnioInicial()
/* 199:    */   {
/* 200:227 */     return this.anioInicial;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setAnioInicial(int anioInicial)
/* 204:    */   {
/* 205:231 */     this.anioInicial = anioInicial;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public int getAnioFinal()
/* 209:    */   {
/* 210:235 */     return this.anioFinal;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setAnioFinal(int anioFinal)
/* 214:    */   {
/* 215:239 */     this.anioFinal = anioFinal;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Date getFechaDesde()
/* 219:    */   {
/* 220:243 */     return this.fechaDesde;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setFechaDesde(Date fechaDesde)
/* 224:    */   {
/* 225:247 */     this.fechaDesde = fechaDesde;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Date getFechaHasta()
/* 229:    */   {
/* 230:251 */     return this.fechaHasta;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setFechaHasta(Date fechaHasta)
/* 234:    */   {
/* 235:255 */     this.fechaHasta = fechaHasta;
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PagoComision
 * JD-Core Version:    0.7.0.1
 */