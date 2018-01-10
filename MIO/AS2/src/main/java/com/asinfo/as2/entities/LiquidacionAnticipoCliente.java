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
/*  29:    */ @Table(name="liquidacion_anticipo_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="fecha")})
/*  30:    */ public class LiquidacionAnticipoCliente
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="liquidacion_anticipo_cliente", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="liquidacion_anticipo_cliente")
/*  37:    */   @Column(name="id_liquidacion_anticipo_cliente")
/*  38:    */   private int idLiquidacionAnticipoCliente;
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
/*  60:    */   @JoinColumn(name="id_anticipo_cliente", nullable=false)
/*  61:    */   private AnticipoCliente anticipoCliente;
/*  62:    */   @Temporal(TemporalType.DATE)
/*  63:    */   @Column(name="fecha", nullable=false)
/*  64:    */   private Date fecha;
/*  65:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  66:    */   private Date fechaContabilizacion;
/*  67:    */   @Column(name="descripcion", length=200, nullable=true)
/*  68:    */   @Size(max=200)
/*  69:    */   private String descripcion;
/*  70:    */   @OneToMany(mappedBy="liquidacionAnticipoCliente", fetch=FetchType.LAZY)
/*  71:104 */   private List<DetalleLiquidacionAnticipoCliente> listaDetalleLiquidacionAnticipoCliente = new ArrayList();
/*  72:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  73:    */   private Integer idDispositivoSincronizacion;
/*  74:    */   
/*  75:    */   public int getId()
/*  76:    */   {
/*  77:115 */     return this.idLiquidacionAnticipoCliente;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdLiquidacionAnticipoCliente()
/*  81:    */   {
/*  82:119 */     return this.idLiquidacionAnticipoCliente;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdLiquidacionAnticipoCliente(int idLiquidacionAnticipoCliente)
/*  86:    */   {
/*  87:123 */     this.idLiquidacionAnticipoCliente = idLiquidacionAnticipoCliente;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdOrganizacion()
/*  91:    */   {
/*  92:127 */     return this.idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdOrganizacion(int idOrganizacion)
/*  96:    */   {
/*  97:131 */     this.idOrganizacion = idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdSucursal()
/* 101:    */   {
/* 102:135 */     return this.idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdSucursal(int idSucursal)
/* 106:    */   {
/* 107:139 */     this.idSucursal = idSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Asiento getAsiento()
/* 111:    */   {
/* 112:143 */     return this.asiento;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setAsiento(Asiento asiento)
/* 116:    */   {
/* 117:147 */     this.asiento = asiento;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public AnticipoCliente getAnticipoCliente()
/* 121:    */   {
/* 122:151 */     return this.anticipoCliente;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setAnticipoCliente(AnticipoCliente anticipoCliente)
/* 126:    */   {
/* 127:155 */     this.anticipoCliente = anticipoCliente;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Date getFecha()
/* 131:    */   {
/* 132:159 */     return this.fecha;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setFecha(Date fecha)
/* 136:    */   {
/* 137:163 */     this.fecha = fecha;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Date getFechaContabilizacion()
/* 141:    */   {
/* 142:167 */     return this.fechaContabilizacion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 146:    */   {
/* 147:171 */     this.fechaContabilizacion = fechaContabilizacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<DetalleLiquidacionAnticipoCliente> getListaDetalleLiquidacionAnticipoCliente()
/* 151:    */   {
/* 152:175 */     return this.listaDetalleLiquidacionAnticipoCliente;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setListaDetalleLiquidacionAnticipoCliente(List<DetalleLiquidacionAnticipoCliente> listaDetalleLiquidacionAnticipoCliente)
/* 156:    */   {
/* 157:180 */     this.listaDetalleLiquidacionAnticipoCliente = listaDetalleLiquidacionAnticipoCliente;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Documento getDocumento()
/* 161:    */   {
/* 162:184 */     return this.documento;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDocumento(Documento documento)
/* 166:    */   {
/* 167:188 */     this.documento = documento;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getNumero()
/* 171:    */   {
/* 172:192 */     return this.numero;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setNumero(String numero)
/* 176:    */   {
/* 177:196 */     this.numero = numero;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getDescripcion()
/* 181:    */   {
/* 182:200 */     return this.descripcion;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setDescripcion(String descripcion)
/* 186:    */   {
/* 187:204 */     this.descripcion = descripcion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String toString()
/* 191:    */   {
/* 192:209 */     return this.numero;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Estado getEstado()
/* 196:    */   {
/* 197:213 */     return this.estado;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setEstado(Estado estado)
/* 201:    */   {
/* 202:217 */     this.estado = estado;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public BigDecimal getValor()
/* 206:    */   {
/* 207:221 */     return this.valor;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setValor(BigDecimal valor)
/* 211:    */   {
/* 212:225 */     this.valor = valor;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Integer getIdDispositivoSincronizacion()
/* 216:    */   {
/* 217:229 */     return this.idDispositivoSincronizacion;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 221:    */   {
/* 222:233 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 223:    */   }
/* 224:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.LiquidacionAnticipoCliente
 * JD-Core Version:    0.7.0.1
 */