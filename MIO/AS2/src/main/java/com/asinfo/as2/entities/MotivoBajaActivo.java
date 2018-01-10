/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ import org.hibernate.annotations.ColumnDefault;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="motivo_baja_activo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  19:    */ public class MotivoBajaActivo
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="motivo_baja_activo", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="motivo_baja_activo")
/*  26:    */   @Column(name="id_motivo_baja_activo")
/*  27:    */   private int idMotivoBajaActivo;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="nombre", nullable=false, length=50)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="codigo", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private String codigo;
/*  39:    */   @Column(name="descripcion", nullable=true)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   private boolean activo;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   private boolean predeterminado;
/*  46:    */   @Column(name="indicador_venta", nullable=false)
/*  47:    */   private boolean indicadorVenta;
/*  48:    */   @ColumnDefault("'0'")
/*  49:    */   @NotNull
/*  50:    */   @Column(name="indicador_fin_vida_util", nullable=false)
/*  51:    */   private boolean indicadorFinVidaUtil;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_cuenta_contable_motivo_baja_activo", nullable=true)
/*  54:    */   private CuentaContable cuentaContableMotivoBajaActivo;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_documento", nullable=false)
/*  57:    */   private Documento documento;
/*  58:    */   
/*  59:    */   public MotivoBajaActivo() {}
/*  60:    */   
/*  61:    */   public MotivoBajaActivo(int idMotivoBajaActivo, String nombre, String codigo)
/*  62:    */   {
/*  63:119 */     this.idMotivoBajaActivo = idMotivoBajaActivo;
/*  64:120 */     this.nombre = nombre;
/*  65:121 */     this.codigo = codigo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdMotivoBajaActivo()
/*  69:    */   {
/*  70:134 */     return this.idMotivoBajaActivo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdMotivoBajaActivo(int idMotivoBajaActivo)
/*  74:    */   {
/*  75:144 */     this.idMotivoBajaActivo = idMotivoBajaActivo;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdOrganizacion()
/*  79:    */   {
/*  80:153 */     return this.idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdOrganizacion(int idOrganizacion)
/*  84:    */   {
/*  85:163 */     this.idOrganizacion = idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdSucursal()
/*  89:    */   {
/*  90:172 */     return this.idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdSucursal(int idSucursal)
/*  94:    */   {
/*  95:182 */     this.idSucursal = idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getNombre()
/*  99:    */   {
/* 100:191 */     return this.nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNombre(String nombre)
/* 104:    */   {
/* 105:201 */     this.nombre = nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getCodigo()
/* 109:    */   {
/* 110:210 */     return this.codigo;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setCodigo(String codigo)
/* 114:    */   {
/* 115:220 */     this.codigo = codigo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getDescripcion()
/* 119:    */   {
/* 120:229 */     return this.descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDescripcion(String descripcion)
/* 124:    */   {
/* 125:239 */     this.descripcion = descripcion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isActivo()
/* 129:    */   {
/* 130:248 */     return this.activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setActivo(boolean activo)
/* 134:    */   {
/* 135:258 */     this.activo = activo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isPredeterminado()
/* 139:    */   {
/* 140:267 */     return this.predeterminado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPredeterminado(boolean predeterminado)
/* 144:    */   {
/* 145:277 */     this.predeterminado = predeterminado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isIndicadorVenta()
/* 149:    */   {
/* 150:286 */     return this.indicadorVenta;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIndicadorVenta(boolean indicadorVenta)
/* 154:    */   {
/* 155:296 */     this.indicadorVenta = indicadorVenta;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public CuentaContable getCuentaContableMotivoBajaActivo()
/* 159:    */   {
/* 160:305 */     return this.cuentaContableMotivoBajaActivo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setCuentaContableMotivoBajaActivo(CuentaContable cuentaContableMotivoBajaActivo)
/* 164:    */   {
/* 165:315 */     this.cuentaContableMotivoBajaActivo = cuentaContableMotivoBajaActivo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Documento getDocumento()
/* 169:    */   {
/* 170:324 */     return this.documento;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setDocumento(Documento documento)
/* 174:    */   {
/* 175:334 */     this.documento = documento;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getId()
/* 179:    */   {
/* 180:344 */     return this.idMotivoBajaActivo;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean isIndicadorFinVidaUtil()
/* 184:    */   {
/* 185:351 */     return this.indicadorFinVidaUtil;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setIndicadorFinVidaUtil(boolean indicadorFinVidaUtil)
/* 189:    */   {
/* 190:358 */     this.indicadorFinVidaUtil = indicadorFinVidaUtil;
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MotivoBajaActivo
 * JD-Core Version:    0.7.0.1
 */