/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.listener.DetalleDepreciacionListener;
/*   4:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EntityListeners;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="detalle_depreciacion", indexes={@javax.persistence.Index(columnList="id_depreciacion")})
/*  26:    */ @EntityListeners({DetalleDepreciacionListener.class})
/*  27:    */ public class DetalleDepreciacion
/*  28:    */   extends EntidadBase
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="detalle_depreciacion", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_depreciacion")
/*  34:    */   @Column(name="id_detalle_depreciacion")
/*  35:    */   private int idDetalleDepreciacion;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   private int idOrganizacion;
/*  38:    */   @Column(name="id_sucursal", nullable=false)
/*  39:    */   private int idSucursal;
/*  40:    */   @Column(name="mes", nullable=false)
/*  41:    */   @Min(0L)
/*  42:    */   private int mes;
/*  43:    */   @Column(name="anio", nullable=false)
/*  44:    */   @Min(0L)
/*  45:    */   private int anio;
/*  46:    */   @Temporal(TemporalType.DATE)
/*  47:    */   @Column(name="fecha", nullable=false)
/*  48:    */   private Date fecha;
/*  49:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51:    */   @Min(0L)
/*  52:    */   private BigDecimal valor;
/*  53:    */   @Column(name="diferencia_temporal", nullable=false, precision=12, scale=2)
/*  54:    */   @Digits(integer=12, fraction=2)
/*  55:    */   private BigDecimal diferenciaTemporal;
/*  56:    */   @Column(name="diferencia_temporal_revalorizacion", nullable=false, precision=12, scale=2)
/*  57:    */   @Digits(integer=12, fraction=2)
/*  58:    */   private BigDecimal diferenciaTemporalRevalorizacion;
/*  59:    */   @Column(name="activo", nullable=false)
/*  60:    */   private boolean activo;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_depreciacion")
/*  63:    */   private Depreciacion depreciacion;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_historico_depreciacion", nullable=true)
/*  66:    */   private HistoricoDepreciacion historicoDepreciacion;
/*  67:    */   @Transient
/*  68:    */   private String traCadenaMes;
/*  69:    */   
/*  70:    */   public int getIdDetalleDepreciacion()
/*  71:    */   {
/*  72:125 */     return this.idDetalleDepreciacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdDetalleDepreciacion(int idDetalleDepreciacion)
/*  76:    */   {
/*  77:135 */     this.idDetalleDepreciacion = idDetalleDepreciacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82:144 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87:154 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdSucursal()
/*  91:    */   {
/*  92:163 */     return this.idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdSucursal(int idSucursal)
/*  96:    */   {
/*  97:173 */     this.idSucursal = idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public BigDecimal getValor()
/* 101:    */   {
/* 102:182 */     return this.valor;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setValor(BigDecimal valor)
/* 106:    */   {
/* 107:192 */     this.valor = valor;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Depreciacion getDepreciacion()
/* 111:    */   {
/* 112:201 */     return this.depreciacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDepreciacion(Depreciacion depreciacion)
/* 116:    */   {
/* 117:211 */     this.depreciacion = depreciacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public HistoricoDepreciacion getHistoricoDepreciacion()
/* 121:    */   {
/* 122:220 */     return this.historicoDepreciacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setHistoricoDepreciacion(HistoricoDepreciacion historicoDepreciacion)
/* 126:    */   {
/* 127:230 */     this.historicoDepreciacion = historicoDepreciacion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getMes()
/* 131:    */   {
/* 132:239 */     return this.mes;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setMes(int mes)
/* 136:    */   {
/* 137:249 */     this.mes = mes;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getAnio()
/* 141:    */   {
/* 142:258 */     return this.anio;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setAnio(int anio)
/* 146:    */   {
/* 147:268 */     this.anio = anio;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getTraCadenaMes()
/* 151:    */   {
/* 152:277 */     this.traCadenaMes = FuncionesUtiles.nombreMes(this.mes - 1);
/* 153:278 */     return this.traCadenaMes;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setTraCadenaMes(String traCadenaMes)
/* 157:    */   {
/* 158:288 */     this.traCadenaMes = traCadenaMes;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getId()
/* 162:    */   {
/* 163:298 */     return this.idDetalleDepreciacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean isActivo()
/* 167:    */   {
/* 168:307 */     return this.activo;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setActivo(boolean activo)
/* 172:    */   {
/* 173:317 */     this.activo = activo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Date getFecha()
/* 177:    */   {
/* 178:321 */     return this.fecha;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setFecha(Date fecha)
/* 182:    */   {
/* 183:325 */     this.fecha = fecha;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public BigDecimal getDiferenciaTemporal()
/* 187:    */   {
/* 188:334 */     return this.diferenciaTemporal;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDiferenciaTemporal(BigDecimal diferenciaTemporal)
/* 192:    */   {
/* 193:344 */     this.diferenciaTemporal = diferenciaTemporal;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public BigDecimal getDiferenciaTemporalRevalorizacion()
/* 197:    */   {
/* 198:353 */     return this.diferenciaTemporalRevalorizacion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setDiferenciaTemporalRevalorizacion(BigDecimal diferenciaTemporalRevalorizacion)
/* 202:    */   {
/* 203:363 */     this.diferenciaTemporalRevalorizacion = diferenciaTemporalRevalorizacion;
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleDepreciacion
 * JD-Core Version:    0.7.0.1
 */