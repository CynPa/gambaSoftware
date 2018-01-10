/*   1:    */ package com.asinfo.as2.entities.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.persistence.Column;
/*  12:    */ import javax.persistence.Entity;
/*  13:    */ import javax.persistence.EnumType;
/*  14:    */ import javax.persistence.Enumerated;
/*  15:    */ import javax.persistence.FetchType;
/*  16:    */ import javax.persistence.GeneratedValue;
/*  17:    */ import javax.persistence.GenerationType;
/*  18:    */ import javax.persistence.Id;
/*  19:    */ import javax.persistence.JoinColumn;
/*  20:    */ import javax.persistence.ManyToOne;
/*  21:    */ import javax.persistence.OneToMany;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Temporal;
/*  25:    */ import javax.persistence.TemporalType;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="carga_archivo")
/*  30:    */ public class CargaArchivo
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   private boolean editado;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="carga_archivo", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="carga_archivo")
/*  38:    */   @Column(name="id_carga_archivo", unique=true, nullable=false)
/*  39:    */   private int idCargaArchivo;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @Column(name="id_sucursal", nullable=false)
/*  43:    */   private int idSucursal;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  46:    */   private Asiento asiento;
/*  47:    */   @Enumerated(EnumType.STRING)
/*  48:    */   @Column(name="estado", length=50, nullable=true)
/*  49:    */   private Estado estado;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_documento", nullable=false)
/*  52:    */   private Documento documento;
/*  53:    */   @Column(name="numero", nullable=false, length=20)
/*  54:    */   @Size(max=20)
/*  55:    */   private String numero;
/*  56:    */   @Column(name="indicador_respaldo", nullable=true)
/*  57:    */   private Boolean indicadorRespaldo;
/*  58:    */   @Column(name="referencia_archivo", nullable=true, length=1000)
/*  59:    */   @Size(max=1000)
/*  60:    */   private String referenciaArchivo;
/*  61:    */   @Column(name="tipo", nullable=true)
/*  62:    */   private String tipo;
/*  63:    */   @Temporal(TemporalType.DATE)
/*  64:    */   @Column(name="fecha", nullable=false)
/*  65:    */   private Date fecha;
/*  66:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="bsp", cascade={javax.persistence.CascadeType.DETACH})
/*  67: 84 */   private List<Ticket> listaTicket = new ArrayList();
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_interfaz_contable_proceso", nullable=true)
/*  70:    */   private InterfazContableProceso interfazContableProceso;
/*  71:    */   @Temporal(TemporalType.DATE)
/*  72:    */   @Column(name="periodo", nullable=true)
/*  73:    */   private Date periodo;
/*  74:    */   
/*  75:    */   public int getId()
/*  76:    */   {
/*  77: 97 */     return this.idCargaArchivo;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdCargaArchivo()
/*  81:    */   {
/*  82:101 */     return this.idCargaArchivo;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdCargaArchivo(int idCargaArchivo)
/*  86:    */   {
/*  87:105 */     this.idCargaArchivo = idCargaArchivo;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<Ticket> getListaTicket()
/*  91:    */   {
/*  92:109 */     return this.listaTicket;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setListaTicket(List<Ticket> listaTicket)
/*  96:    */   {
/*  97:113 */     this.listaTicket = listaTicket;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdOrganizacion()
/* 101:    */   {
/* 102:117 */     return this.idOrganizacion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdOrganizacion(int idOrganizacion)
/* 106:    */   {
/* 107:121 */     this.idOrganizacion = idOrganizacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdSucursal()
/* 111:    */   {
/* 112:125 */     return this.idSucursal;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdSucursal(int idSucursal)
/* 116:    */   {
/* 117:129 */     this.idSucursal = idSucursal;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Asiento getAsiento()
/* 121:    */   {
/* 122:133 */     return this.asiento;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setAsiento(Asiento asiento)
/* 126:    */   {
/* 127:137 */     this.asiento = asiento;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Estado getEstado()
/* 131:    */   {
/* 132:141 */     return this.estado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setEstado(Estado estado)
/* 136:    */   {
/* 137:145 */     this.estado = estado;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Documento getDocumento()
/* 141:    */   {
/* 142:149 */     return this.documento;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDocumento(Documento documento)
/* 146:    */   {
/* 147:153 */     this.documento = documento;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getNumero()
/* 151:    */   {
/* 152:157 */     return this.numero;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setNumero(String numero)
/* 156:    */   {
/* 157:161 */     this.numero = numero;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isEditado()
/* 161:    */   {
/* 162:165 */     return this.editado;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setEditado(boolean editado)
/* 166:    */   {
/* 167:169 */     this.editado = editado;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Boolean getIndicadorRespaldo()
/* 171:    */   {
/* 172:173 */     return Boolean.valueOf(this.indicadorRespaldo == null ? false : this.indicadorRespaldo.booleanValue());
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setIndicadorRespaldo(Boolean indicadorRespaldo)
/* 176:    */   {
/* 177:177 */     this.indicadorRespaldo = indicadorRespaldo;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getReferenciaArchivo()
/* 181:    */   {
/* 182:181 */     return this.referenciaArchivo;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setReferenciaArchivo(String referenciaArchivo)
/* 186:    */   {
/* 187:185 */     this.referenciaArchivo = referenciaArchivo;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getTipo()
/* 191:    */   {
/* 192:189 */     return this.tipo;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setTipo(String tipo)
/* 196:    */   {
/* 197:193 */     this.tipo = tipo;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Date getFecha()
/* 201:    */   {
/* 202:197 */     return this.fecha;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setFecha(Date fecha)
/* 206:    */   {
/* 207:201 */     this.fecha = fecha;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Date getPeriodo()
/* 211:    */   {
/* 212:205 */     return this.periodo;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setPeriodo(Date periodo)
/* 216:    */   {
/* 217:209 */     this.periodo = periodo;
/* 218:    */   }
/* 219:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.aerolineas.CargaArchivo
 * JD-Core Version:    0.7.0.1
 */