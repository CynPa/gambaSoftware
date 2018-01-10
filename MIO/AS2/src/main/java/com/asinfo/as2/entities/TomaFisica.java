/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
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
/*  23:    */ import javax.persistence.Transient;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="toma_fisica", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})})
/*  29:    */ public class TomaFisica
/*  30:    */   extends EntidadBase
/*  31:    */   implements Serializable
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 2039528659887782686L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="toma_fisica", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="toma_fisica")
/*  37:    */   @Column(name="id_toma_fisica")
/*  38:    */   private int idTomaFisica;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_documento", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private Documento documento;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_documento_ajuste_ingreso", nullable=true)
/*  45:    */   private Documento documentoAjusteIngreso;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_documento_ajuste_egreso", nullable=true)
/*  48:    */   private Documento documentoAjusteEgreso;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_motivo_ajuste_inventario", nullable=true)
/*  51:    */   private MotivoAjusteInventario motivoAjusteInventario;
/*  52:    */   @Column(name="numero", nullable=false, length=20)
/*  53:    */   private String numero;
/*  54:    */   @Column(name="id_organizacion", nullable=true)
/*  55:    */   private int idOrganizacion;
/*  56:    */   @Column(name="id_sucursal", nullable=true)
/*  57:    */   private int idSucursal;
/*  58:    */   @Column(name="descripcion", length=200, nullable=true)
/*  59:    */   @Size(max=200)
/*  60:    */   private String descripcion;
/*  61:    */   @Temporal(TemporalType.DATE)
/*  62:    */   @Column(name="fecha", nullable=false)
/*  63:    */   @NotNull
/*  64:    */   private Date fecha;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  67:    */   @NotNull
/*  68:    */   private Bodega bodega;
/*  69:    */   @Column(name="estado", nullable=false)
/*  70:    */   @Enumerated(EnumType.ORDINAL)
/*  71:    */   private Estado estado;
/*  72:    */   @OneToMany(mappedBy="tomaFisica", fetch=FetchType.LAZY)
/*  73:102 */   private List<DetalleTomaFisica> listaDetalleTomaFisica = new ArrayList();
/*  74:    */   @Transient
/*  75:    */   private String traNombreBodega;
/*  76:    */   
/*  77:    */   public TomaFisica() {}
/*  78:    */   
/*  79:    */   public TomaFisica(int idTomaFisica, String numero, String descripcion, Date fecha, String traNombreBodega, Estado estado)
/*  80:    */   {
/*  81:127 */     this.idTomaFisica = idTomaFisica;
/*  82:128 */     this.numero = numero;
/*  83:129 */     this.descripcion = descripcion;
/*  84:130 */     this.fecha = fecha;
/*  85:131 */     this.estado = estado;
/*  86:132 */     this.traNombreBodega = traNombreBodega;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public TomaFisica(int idTomaFisica, String numero)
/*  90:    */   {
/*  91:143 */     this.idTomaFisica = idTomaFisica;
/*  92:144 */     this.numero = numero;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getId()
/*  96:    */   {
/*  97:154 */     return this.idTomaFisica;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdTomaFisica()
/* 101:    */   {
/* 102:165 */     return this.idTomaFisica;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdTomaFisica(int idTomaFisica)
/* 106:    */   {
/* 107:175 */     this.idTomaFisica = idTomaFisica;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Documento getDocumento()
/* 111:    */   {
/* 112:184 */     return this.documento;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDocumento(Documento documento)
/* 116:    */   {
/* 117:194 */     this.documento = documento;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getNumero()
/* 121:    */   {
/* 122:203 */     return this.numero;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setNumero(String numero)
/* 126:    */   {
/* 127:213 */     this.numero = numero;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getIdOrganizacion()
/* 131:    */   {
/* 132:222 */     return this.idOrganizacion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setIdOrganizacion(int idOrganizacion)
/* 136:    */   {
/* 137:232 */     this.idOrganizacion = idOrganizacion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getIdSucursal()
/* 141:    */   {
/* 142:241 */     return this.idSucursal;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setIdSucursal(int idSucursal)
/* 146:    */   {
/* 147:251 */     this.idSucursal = idSucursal;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getDescripcion()
/* 151:    */   {
/* 152:260 */     return this.descripcion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setDescripcion(String descripcion)
/* 156:    */   {
/* 157:270 */     this.descripcion = descripcion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Date getFecha()
/* 161:    */   {
/* 162:279 */     return this.fecha;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFecha(Date fecha)
/* 166:    */   {
/* 167:289 */     this.fecha = fecha;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Bodega getBodega()
/* 171:    */   {
/* 172:298 */     return this.bodega;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setBodega(Bodega bodega)
/* 176:    */   {
/* 177:308 */     this.bodega = bodega;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Estado getEstado()
/* 181:    */   {
/* 182:317 */     return this.estado;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setEstado(Estado estado)
/* 186:    */   {
/* 187:327 */     this.estado = estado;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List<DetalleTomaFisica> getListaDetalleTomaFisica()
/* 191:    */   {
/* 192:336 */     return this.listaDetalleTomaFisica;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setListaDetalleTomaFisica(List<DetalleTomaFisica> listaDetalleTomaFisica)
/* 196:    */   {
/* 197:346 */     this.listaDetalleTomaFisica = listaDetalleTomaFisica;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getTraNombreBodega()
/* 201:    */   {
/* 202:355 */     return this.traNombreBodega;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setTraNombreBodega(String traNombreBodega)
/* 206:    */   {
/* 207:365 */     this.traNombreBodega = traNombreBodega;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Documento getDocumentoAjusteIngreso()
/* 211:    */   {
/* 212:369 */     return this.documentoAjusteIngreso;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDocumentoAjusteIngreso(Documento documentoAjusteIngreso)
/* 216:    */   {
/* 217:373 */     this.documentoAjusteIngreso = documentoAjusteIngreso;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Documento getDocumentoAjusteEgreso()
/* 221:    */   {
/* 222:377 */     return this.documentoAjusteEgreso;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setDocumentoAjusteEgreso(Documento documentoAjusteEgreso)
/* 226:    */   {
/* 227:381 */     this.documentoAjusteEgreso = documentoAjusteEgreso;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public MotivoAjusteInventario getMotivoAjusteInventario()
/* 231:    */   {
/* 232:385 */     return this.motivoAjusteInventario;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setMotivoAjusteInventario(MotivoAjusteInventario motivoAjusteInventario)
/* 236:    */   {
/* 237:389 */     this.motivoAjusteInventario = motivoAjusteInventario;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String toString()
/* 241:    */   {
/* 242:394 */     return this.numero;
/* 243:    */   }
/* 244:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TomaFisica
 * JD-Core Version:    0.7.0.1
 */