/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="detalle_extracto_bancario")
/*  20:    */ public class DetalleExtractoBancario
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="detalle_extracto_bancario", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_extracto_bancario")
/*  28:    */   @Column(name="id_detalle_extracto_bancario")
/*  29:    */   private int idDetalleExtractoBancario;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_configuracion_extracto_bancario")
/*  36:    */   private ExtractoBancario extractoBancario;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  39:    */   private CuentaContable cuentaContable;
/*  40:    */   @Column(name="criterio_de_busqueda", length=200)
/*  41:    */   @Size(max=200)
/*  42:    */   private String criterioDeBusqueda;
/*  43:    */   @Column(name="criterio_de_busqueda2", length=200)
/*  44:    */   @Size(max=200)
/*  45:    */   private String criterioDeBusqueda2;
/*  46:    */   @NotNull
/*  47:    */   @Column(name="monto", nullable=false)
/*  48: 69 */   private BigDecimal monto = BigDecimal.ZERO;
/*  49:    */   @Column(name="descripcion", length=200)
/*  50:    */   @Size(max=200)
/*  51:    */   private String descripcion;
/*  52:    */   @Column(name="descripcion2_cuenta_banco", length=200)
/*  53:    */   @Size(max=200)
/*  54:    */   private String descripcion2CuentaBanco;
/*  55:    */   @Column(name="descripcion2_cuenta_contrapartida", length=200)
/*  56:    */   @Size(max=200)
/*  57:    */   private String descripcion2CuentaContrapartida;
/*  58:    */   @Column(name="indicador_modifica_cuenta_contable")
/*  59:    */   private boolean indicadorModificaCuentaContable;
/*  60:    */   
/*  61:    */   public DetalleExtractoBancario() {}
/*  62:    */   
/*  63:    */   public DetalleExtractoBancario(ExtractoBancario extractoBancario, CuentaContable cuentaContable, String criterioDeBusqueda, String criterioDeBusqueda2, BigDecimal monto, String descripcion, String descripcion2CuentaBanco, String descripcion2CuentaContrapartida, boolean indicadorModificaCuentaContable)
/*  64:    */   {
/*  65: 96 */     this(cuentaContable, criterioDeBusqueda, criterioDeBusqueda2, monto, descripcion, descripcion2CuentaBanco, descripcion2CuentaContrapartida, indicadorModificaCuentaContable);
/*  66:    */     
/*  67: 98 */     this.extractoBancario = extractoBancario;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public DetalleExtractoBancario(CuentaContable cuentaContable, String criterioDeBusqueda, String criterioDeBusqueda2, BigDecimal monto, String descripcion, String descripcion2CuentaBanco, String descripcion2CuentaContrapartida, boolean indicadorModificaCuentaContable)
/*  71:    */   {
/*  72:104 */     this.cuentaContable = cuentaContable;
/*  73:105 */     this.criterioDeBusqueda = criterioDeBusqueda;
/*  74:106 */     this.criterioDeBusqueda2 = criterioDeBusqueda2;
/*  75:107 */     this.monto = monto;
/*  76:108 */     this.descripcion = descripcion;
/*  77:109 */     this.descripcion2CuentaBanco = descripcion2CuentaBanco;
/*  78:110 */     this.descripcion2CuentaContrapartida = descripcion2CuentaContrapartida;
/*  79:111 */     this.indicadorModificaCuentaContable = indicadorModificaCuentaContable;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getId()
/*  83:    */   {
/*  84:116 */     return this.idDetalleExtractoBancario;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdDetalleExtractoBancario()
/*  88:    */   {
/*  89:120 */     return this.idDetalleExtractoBancario;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdDetalleExtractoBancario(int idDetalleExtractoBancario)
/*  93:    */   {
/*  94:124 */     this.idDetalleExtractoBancario = idDetalleExtractoBancario;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getIdOrganizacion()
/*  98:    */   {
/*  99:128 */     return this.idOrganizacion;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setIdOrganizacion(int idOrganizacion)
/* 103:    */   {
/* 104:132 */     this.idOrganizacion = idOrganizacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getIdSucursal()
/* 108:    */   {
/* 109:136 */     return this.idSucursal;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setIdSucursal(int idSucursal)
/* 113:    */   {
/* 114:140 */     this.idSucursal = idSucursal;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ExtractoBancario getExtractoBancario()
/* 118:    */   {
/* 119:144 */     return this.extractoBancario;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setExtractoBancario(ExtractoBancario extractoBancario)
/* 123:    */   {
/* 124:148 */     this.extractoBancario = extractoBancario;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getDescripcion()
/* 128:    */   {
/* 129:152 */     return this.descripcion == null ? "" : this.descripcion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDescripcion(String descripcion)
/* 133:    */   {
/* 134:156 */     this.descripcion = descripcion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public CuentaContable getCuentaContable()
/* 138:    */   {
/* 139:160 */     return this.cuentaContable;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 143:    */   {
/* 144:164 */     this.cuentaContable = cuentaContable;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public BigDecimal getMonto()
/* 148:    */   {
/* 149:168 */     return this.monto;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setMonto(BigDecimal monto)
/* 153:    */   {
/* 154:172 */     this.monto = monto;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getCriterioDeBusqueda()
/* 158:    */   {
/* 159:176 */     return this.criterioDeBusqueda == null ? "" : this.criterioDeBusqueda;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setCriterioDeBusqueda(String criterioDeBusqueda)
/* 163:    */   {
/* 164:180 */     this.criterioDeBusqueda = criterioDeBusqueda;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getCriterioDeBusqueda2()
/* 168:    */   {
/* 169:184 */     return this.criterioDeBusqueda2 == null ? "" : this.criterioDeBusqueda2;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setCriterioDeBusqueda2(String criterioDeBusqueda2)
/* 173:    */   {
/* 174:188 */     this.criterioDeBusqueda2 = criterioDeBusqueda2;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getDescripcion2CuentaBanco()
/* 178:    */   {
/* 179:192 */     return this.descripcion2CuentaBanco == null ? "" : this.descripcion2CuentaBanco;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setDescripcion2CuentaBanco(String descripcion2CuentaBanco)
/* 183:    */   {
/* 184:196 */     this.descripcion2CuentaBanco = descripcion2CuentaBanco;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getDescripcion2CuentaContrapartida()
/* 188:    */   {
/* 189:200 */     return this.descripcion2CuentaContrapartida == null ? "" : this.descripcion2CuentaContrapartida;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setDescripcion2CuentaContrapartida(String descripcion2CuentaContrapartida)
/* 193:    */   {
/* 194:204 */     this.descripcion2CuentaContrapartida = descripcion2CuentaContrapartida;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean isIndicadorModificaCuentaContable()
/* 198:    */   {
/* 199:208 */     return this.indicadorModificaCuentaContable;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setIndicadorModificaCuentaContable(boolean indicadorModificaCuentaContable)
/* 203:    */   {
/* 204:212 */     this.indicadorModificaCuentaContable = indicadorModificaCuentaContable;
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleExtractoBancario
 * JD-Core Version:    0.7.0.1
 */