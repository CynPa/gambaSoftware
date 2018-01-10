/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="concepto_contable", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  19:    */ public class ConceptoContable
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="concepto_contable", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="concepto_contable")
/*  27:    */   @Column(name="id_concepto_contable", unique=true, nullable=false)
/*  28:    */   private int idConceptoContable;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="codigo", nullable=false, length=10)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=10)
/*  36:    */   private String codigo;
/*  37:    */   @Column(name="nombre", nullable=false, length=50)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=2, max=50)
/*  40:    */   private String nombre;
/*  41:    */   @Column(name="descripcion", nullable=true, length=200)
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   @Column(name="predeterminado", nullable=false)
/*  47:    */   private boolean predeterminado;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_cuenta_contable_debe", nullable=false)
/*  50:    */   private CuentaContable cuentaContableDebe;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_cuenta_contable_haber", nullable=false)
/*  53:    */   private CuentaContable cuentaContableHaber;
/*  54:    */   
/*  55:    */   public ConceptoContable() {}
/*  56:    */   
/*  57:    */   public ConceptoContable(int idConceptoContable, String codigo, String nombre, String descripcion, boolean activo)
/*  58:    */   {
/*  59: 75 */     this.idConceptoContable = idConceptoContable;
/*  60: 76 */     this.codigo = codigo;
/*  61: 77 */     this.nombre = nombre;
/*  62: 78 */     this.descripcion = descripcion;
/*  63: 79 */     this.activo = activo;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ConceptoContable(int idConceptoContable, String codigo, String nombre)
/*  67:    */   {
/*  68: 83 */     this.idConceptoContable = idConceptoContable;
/*  69: 84 */     this.codigo = codigo;
/*  70: 85 */     this.nombre = nombre;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getId()
/*  74:    */   {
/*  75: 90 */     return this.idConceptoContable;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdConceptoContable()
/*  79:    */   {
/*  80: 99 */     return this.idConceptoContable;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdConceptoContable(int idConceptoContable)
/*  84:    */   {
/*  85:109 */     this.idConceptoContable = idConceptoContable;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdOrganizacion()
/*  89:    */   {
/*  90:118 */     return this.idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdOrganizacion(int idOrganizacion)
/*  94:    */   {
/*  95:128 */     this.idOrganizacion = idOrganizacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdSucursal()
/*  99:    */   {
/* 100:137 */     return this.idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdSucursal(int idSucursal)
/* 104:    */   {
/* 105:147 */     this.idSucursal = idSucursal;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getCodigo()
/* 109:    */   {
/* 110:156 */     return this.codigo;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setCodigo(String codigo)
/* 114:    */   {
/* 115:166 */     this.codigo = codigo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getNombre()
/* 119:    */   {
/* 120:175 */     return this.nombre;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setNombre(String nombre)
/* 124:    */   {
/* 125:185 */     this.nombre = nombre;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getDescripcion()
/* 129:    */   {
/* 130:194 */     return this.descripcion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setDescripcion(String descripcion)
/* 134:    */   {
/* 135:204 */     this.descripcion = descripcion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isActivo()
/* 139:    */   {
/* 140:213 */     return this.activo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setActivo(boolean activo)
/* 144:    */   {
/* 145:223 */     this.activo = activo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isPredeterminado()
/* 149:    */   {
/* 150:232 */     return this.predeterminado;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setPredeterminado(boolean predeterminado)
/* 154:    */   {
/* 155:242 */     this.predeterminado = predeterminado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public CuentaContable getCuentaContableDebe()
/* 159:    */   {
/* 160:251 */     return this.cuentaContableDebe;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setCuentaContableDebe(CuentaContable cuentaContableDebe)
/* 164:    */   {
/* 165:261 */     this.cuentaContableDebe = cuentaContableDebe;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public CuentaContable getCuentaContableHaber()
/* 169:    */   {
/* 170:270 */     return this.cuentaContableHaber;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setCuentaContableHaber(CuentaContable cuentaContableHaber)
/* 174:    */   {
/* 175:280 */     this.cuentaContableHaber = cuentaContableHaber;
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ConceptoContable
 * JD-Core Version:    0.7.0.1
 */