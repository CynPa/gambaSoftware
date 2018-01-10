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
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="rubro_departamento_cuenta_contable", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_departamento", "id_rubro"})})
/*  16:    */ public class RubroDepartamentoCuentaContable
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   @Column(name="id_organizacion", nullable=false)
/*  21:    */   private int idOrganizacion;
/*  22:    */   @Column(name="id_sucursal", nullable=false)
/*  23:    */   private int idSucursal;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="rubro_departamento_cuenta_contable", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rubro_departamento_cuenta_contable")
/*  27:    */   @Column(name="id_rubro_departamento_cuenta_contable")
/*  28:    */   private int idRubroDepartamentoCuentaContable;
/*  29:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  30:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  31:    */   private CuentaContable cuentaContableRubro;
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_departamento", nullable=true)
/*  34:    */   private Departamento departamento;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_rubro", nullable=true)
/*  37:    */   private Rubro rubro;
/*  38:    */   
/*  39:    */   public int getId()
/*  40:    */   {
/*  41: 96 */     return this.idRubroDepartamentoCuentaContable;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getIdRubroDepartamentoCuentaContable()
/*  45:    */   {
/*  46:105 */     return this.idRubroDepartamentoCuentaContable;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setIdRubroDepartamentoCuentaContable(int idRubroDepartamentoCuentaContable)
/*  50:    */   {
/*  51:115 */     this.idRubroDepartamentoCuentaContable = idRubroDepartamentoCuentaContable;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public CuentaContable getCuentaContableRubro()
/*  55:    */   {
/*  56:124 */     return this.cuentaContableRubro;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setCuentaContableRubro(CuentaContable cuentaContableRubro)
/*  60:    */   {
/*  61:134 */     this.cuentaContableRubro = cuentaContableRubro;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdOrganizacion()
/*  65:    */   {
/*  66:143 */     return this.idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71:153 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdSucursal()
/*  75:    */   {
/*  76:162 */     return this.idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdSucursal(int idSucursal)
/*  80:    */   {
/*  81:172 */     this.idSucursal = idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Departamento getDepartamento()
/*  85:    */   {
/*  86:181 */     return this.departamento;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setDepartamento(Departamento departamento)
/*  90:    */   {
/*  91:191 */     this.departamento = departamento;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Rubro getRubro()
/*  95:    */   {
/*  96:200 */     return this.rubro;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setRubro(Rubro rubro)
/* 100:    */   {
/* 101:210 */     this.rubro = rubro;
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RubroDepartamentoCuentaContable
 * JD-Core Version:    0.7.0.1
 */