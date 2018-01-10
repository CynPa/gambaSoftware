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
/*  19:    */ import javax.persistence.OneToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ import org.hibernate.annotations.Fetch;
/*  27:    */ import org.hibernate.annotations.FetchMode;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="hoja_ruta", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})})
/*  31:    */ public class HojaRuta
/*  32:    */   extends EntidadBase
/*  33:    */   implements Serializable
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="hoja_ruta", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="hoja_ruta")
/*  39:    */   @Column(name="id_hoja_ruta", unique=true, nullable=false)
/*  40:    */   private int idHojaRuta;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_documento", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private Documento documento;
/*  45:    */   @Column(name="id_organizacion", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private int idOrganizacion;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private Sucursal sucursal;
/*  52:    */   @Temporal(TemporalType.DATE)
/*  53:    */   @Column(name="fecha", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   private Date fecha;
/*  56:    */   @Column(name="numero", nullable=false, length=20)
/*  57:    */   @NotNull
/*  58:    */   @Size(max=20)
/*  59:    */   private String numero;
/*  60:    */   @Column(name="descripcion", length=200, nullable=true)
/*  61:    */   @Size(max=200)
/*  62: 86 */   private String descripcion = "";
/*  63:    */   @Column(name="responsable", length=100, nullable=true)
/*  64:    */   @Size(max=100)
/*  65: 90 */   private String responsable = "";
/*  66:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="hojaRuta")
/*  67: 94 */   private List<DetalleHojaRuta> listaDetalleHojaRuta = new ArrayList();
/*  68:    */   @Column(name="indicador_hoja_ruta_transportista", nullable=true)
/*  69:    */   private Boolean indicadorHojaRutaTransportista;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_transportista", nullable=true)
/*  72:    */   private Transportista transportista;
/*  73:    */   @Column(name="estado", nullable=false)
/*  74:    */   @Enumerated(EnumType.ORDINAL)
/*  75:    */   private Estado estado;
/*  76:    */   @OneToOne(fetch=FetchType.LAZY, mappedBy="hojaRutaTransportista")
/*  77:    */   @Fetch(FetchMode.JOIN)
/*  78:    */   private GuiaRemision guiaRemision;
/*  79:    */   
/*  80:    */   public int getId()
/*  81:    */   {
/*  82:126 */     return this.idHojaRuta;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdHojaRuta()
/*  86:    */   {
/*  87:130 */     return this.idHojaRuta;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdHojaRuta(int idHojaRuta)
/*  91:    */   {
/*  92:134 */     this.idHojaRuta = idHojaRuta;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Documento getDocumento()
/*  96:    */   {
/*  97:138 */     return this.documento;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setDocumento(Documento documento)
/* 101:    */   {
/* 102:142 */     this.documento = documento;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getIdOrganizacion()
/* 106:    */   {
/* 107:146 */     return this.idOrganizacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setIdOrganizacion(int idOrganizacion)
/* 111:    */   {
/* 112:150 */     this.idOrganizacion = idOrganizacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Sucursal getSucursal()
/* 116:    */   {
/* 117:154 */     return this.sucursal;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setSucursal(Sucursal sucursal)
/* 121:    */   {
/* 122:158 */     this.sucursal = sucursal;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Date getFecha()
/* 126:    */   {
/* 127:162 */     return this.fecha;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setFecha(Date fecha)
/* 131:    */   {
/* 132:166 */     this.fecha = fecha;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getNumero()
/* 136:    */   {
/* 137:170 */     return this.numero;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setNumero(String numero)
/* 141:    */   {
/* 142:174 */     this.numero = numero;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getDescripcion()
/* 146:    */   {
/* 147:178 */     return this.descripcion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setDescripcion(String descripcion)
/* 151:    */   {
/* 152:182 */     this.descripcion = descripcion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<DetalleHojaRuta> getListaDetalleHojaRuta()
/* 156:    */   {
/* 157:186 */     return this.listaDetalleHojaRuta;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaDetalleHojaRuta(List<DetalleHojaRuta> listaDetalleHojaRuta)
/* 161:    */   {
/* 162:190 */     this.listaDetalleHojaRuta = listaDetalleHojaRuta;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getResponsable()
/* 166:    */   {
/* 167:194 */     return this.responsable;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setResponsable(String responsable)
/* 171:    */   {
/* 172:198 */     this.responsable = responsable;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Boolean getIndicadorHojaRutaTransportista()
/* 176:    */   {
/* 177:202 */     return Boolean.valueOf(this.indicadorHojaRutaTransportista == null ? false : this.indicadorHojaRutaTransportista.booleanValue());
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setIndicadorHojaRutaTransportista(Boolean indicadorHojaRutaTransportista)
/* 181:    */   {
/* 182:206 */     this.indicadorHojaRutaTransportista = indicadorHojaRutaTransportista;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Transportista getTransportista()
/* 186:    */   {
/* 187:210 */     return this.transportista;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setTransportista(Transportista transportista)
/* 191:    */   {
/* 192:214 */     this.transportista = transportista;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Estado getEstado()
/* 196:    */   {
/* 197:218 */     return this.estado;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setEstado(Estado estado)
/* 201:    */   {
/* 202:222 */     this.estado = estado;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public GuiaRemision getGuiaRemision()
/* 206:    */   {
/* 207:226 */     return this.guiaRemision;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setGuiaRemision(GuiaRemision guiaRemision)
/* 211:    */   {
/* 212:230 */     this.guiaRemision = guiaRemision;
/* 213:    */   }
/* 214:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.HojaRuta
 * JD-Core Version:    0.7.0.1
 */