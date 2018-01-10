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
/*  15:    */ @Table(name="cuenta_contable_cruce_cuenta_bancaria_organizacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_cuenta_bancaria_organizacion", "id_cuenta_contable"})})
/*  16:    */ public class CuentaContableCruceCuentaBancariaOrganizacion
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 5801702482317655194L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="cuenta_contable_cruce_cuenta_bancaria_organizacion", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cuenta_contable_cruce_cuenta_bancaria_organizacion")
/*  23:    */   @Column(name="id_cuenta_contable_cruce_cuenta_bancaria_organizacion")
/*  24:    */   private int idCuentaContableCruceCuentaBancariaOrganizacion;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  28:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  29:    */   private CuentaContable cuentaContable;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*  32:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  33:    */   
/*  34:    */   public int getId()
/*  35:    */   {
/*  36: 86 */     return this.idCuentaContableCruceCuentaBancariaOrganizacion;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getIdCuentaContableCruceCuentaBancariaOrganizacion()
/*  40:    */   {
/*  41: 90 */     return this.idCuentaContableCruceCuentaBancariaOrganizacion;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIdCuentaContableCruceCuentaBancariaOrganizacion(int idCuentaContableCruceCuentaBancariaOrganizacion)
/*  45:    */   {
/*  46: 94 */     this.idCuentaContableCruceCuentaBancariaOrganizacion = idCuentaContableCruceCuentaBancariaOrganizacion;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdOrganizacion()
/*  50:    */   {
/*  51: 98 */     return this.idOrganizacion;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdOrganizacion(int idOrganizacion)
/*  55:    */   {
/*  56:102 */     this.idOrganizacion = idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/*  60:    */   {
/*  61:106 */     return this.cuentaBancariaOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/*  65:    */   {
/*  66:110 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CuentaContable getCuentaContable()
/*  70:    */   {
/*  71:114 */     return this.cuentaContable;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setCuentaContable(CuentaContable cuentaContable)
/*  75:    */   {
/*  76:118 */     this.cuentaContable = cuentaContable;
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CuentaContableCruceCuentaBancariaOrganizacion
 * JD-Core Version:    0.7.0.1
 */