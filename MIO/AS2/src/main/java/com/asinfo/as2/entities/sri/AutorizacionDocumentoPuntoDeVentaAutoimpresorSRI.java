/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   5:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.Max;
/*  19:    */ import javax.validation.constraints.Min;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="autorizacion_documento_punto_de_venta_autoimpresor_SRI")
/*  24:    */ public class AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="autorizacion_documento_punto_de_venta_autoimpresor_SRI", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="autorizacion_documento_punto_de_venta_autoimpresor_SRI")
/*  31:    */   @Column(name="id_autorizacion_documento_punto_de_venta_autoimpresor_SRI")
/*  32:    */   private int idAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @ManyToOne
/*  38:    */   @JoinColumn(name="id_autorizacion_autoimpresor_SRI", nullable=true)
/*  39:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  40:    */   @Enumerated(EnumType.ORDINAL)
/*  41:    */   @Column(name="documento_base", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private DocumentoBase documentoBase;
/*  44:    */   @ManyToOne
/*  45:    */   @JoinColumn(name="id_punto_de_venta", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private PuntoDeVenta puntoDeVenta;
/*  48:    */   @Column(name="fecha_inclusion", nullable=true)
/*  49:    */   private Date fechaInclusion;
/*  50:    */   @Column(name="fecha_exclusion", nullable=true)
/*  51:    */   private Date fechaExclusion;
/*  52:    */   @Column(name="indicador_impreso", nullable=false)
/*  53:    */   private boolean indicadorImpreso;
/*  54:    */   @Column(name="indicador_nuevo", nullable=false)
/*  55:    */   private boolean indicadorNuevo;
/*  56:    */   @Column(name="numero_inicial", nullable=false)
/*  57:    */   @NotNull
/*  58:    */   @Min(0L)
/*  59:    */   @Max(999999999L)
/*  60:    */   private int numeroInicial;
/*  61:    */   @Column(name="numero", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   @Min(1L)
/*  64:    */   @Max(999999999L)
/*  65:    */   private int numero;
/*  66:    */   @Column(name="numero_anterior", nullable=false)
/*  67:    */   @NotNull
/*  68:    */   @Min(0L)
/*  69:    */   @Max(999999999L)
/*  70:    */   private int numeroAnterior;
/*  71:    */   @Column(name="activo", nullable=false)
/*  72:    */   private boolean activo;
/*  73:    */   
/*  74:    */   public int getId()
/*  75:    */   {
/*  76:110 */     return this.idAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/*  80:    */   {
/*  81:114 */     return this.idAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(int idAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)
/*  85:    */   {
/*  86:118 */     this.idAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI = idAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdOrganizacion()
/*  90:    */   {
/*  91:122 */     return this.idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdOrganizacion(int idOrganizacion)
/*  95:    */   {
/*  96:126 */     this.idOrganizacion = idOrganizacion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdSucursal()
/* 100:    */   {
/* 101:130 */     return this.idSucursal;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdSucursal(int idSucursal)
/* 105:    */   {
/* 106:134 */     this.idSucursal = idSucursal;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isActivo()
/* 110:    */   {
/* 111:138 */     return this.activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setActivo(boolean activo)
/* 115:    */   {
/* 116:142 */     this.activo = activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/* 120:    */   {
/* 121:146 */     return this.autorizacionAutoimpresorSRI;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 125:    */   {
/* 126:150 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public DocumentoBase getDocumentoBase()
/* 130:    */   {
/* 131:154 */     return this.documentoBase;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 135:    */   {
/* 136:158 */     this.documentoBase = documentoBase;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public PuntoDeVenta getPuntoDeVenta()
/* 140:    */   {
/* 141:162 */     return this.puntoDeVenta;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 145:    */   {
/* 146:166 */     this.puntoDeVenta = puntoDeVenta;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Date getFechaInclusion()
/* 150:    */   {
/* 151:170 */     return this.fechaInclusion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setFechaInclusion(Date fechaInclusion)
/* 155:    */   {
/* 156:174 */     this.fechaInclusion = fechaInclusion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Date getFechaExclusion()
/* 160:    */   {
/* 161:178 */     return this.fechaExclusion;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setFechaExclusion(Date fechaExclusion)
/* 165:    */   {
/* 166:182 */     this.fechaExclusion = fechaExclusion;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isIndicadorImpreso()
/* 170:    */   {
/* 171:186 */     return this.indicadorImpreso;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setIndicadorImpreso(boolean indicadorImpreso)
/* 175:    */   {
/* 176:190 */     this.indicadorImpreso = indicadorImpreso;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int getNumeroInicial()
/* 180:    */   {
/* 181:194 */     return this.numeroInicial;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setNumeroInicial(int numeroInicial)
/* 185:    */   {
/* 186:198 */     this.numeroInicial = numeroInicial;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public int getNumero()
/* 190:    */   {
/* 191:202 */     return this.numero;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setNumero(int numero)
/* 195:    */   {
/* 196:206 */     this.numero = numero;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int getNumeroAnterior()
/* 200:    */   {
/* 201:210 */     return this.numeroAnterior;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setNumeroAnterior(int numeroAnterior)
/* 205:    */   {
/* 206:214 */     this.numeroAnterior = numeroAnterior;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isIndicadorNuevo()
/* 210:    */   {
/* 211:218 */     return this.indicadorNuevo;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setIndicadorNuevo(boolean indicadorNuevo)
/* 215:    */   {
/* 216:222 */     this.indicadorNuevo = indicadorNuevo;
/* 217:    */   }
/* 218:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI
 * JD-Core Version:    0.7.0.1
 */